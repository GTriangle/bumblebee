<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
    <script>
        /**
         * 全局的变量对象
         */
        var menus = '[{"name":"首页","router":"index.html","icon":"icon-home","sub":[],"key":"index"},{"name":"用户管理","router":"member_list.html","icon":"icon-users","sub":[{"name":"用户列表","router":"member_list.html"},{"name":"用户举报","router":"member_report.html"}],"key":"member"},{"name":"认证管理","router":"auth-index.html","icon":"icon-emp-following","sub":[{"name":"头像认证","router":"auth-header.html"},{"name":"实名认证","router":"auth-name.html"}],"key":"auth"},{"name":"图片管理","router":"picture.html","icon":"icon-picture","sub":[{"name":"照片审核","router":"picture-index.html"},{"name":"头像审核","router":"header-index.html"}],"key":"picture"}]';


        var isDebug = <%=IS_DEBUG%>;
        var domain = {
            base: '<%=fc_base_domain%>', //ajax请求的前缀
            account: '<%=fc_account_domain%>', //登录地址
            lib: '<%=fc_cdn_lib%>', //公共js类库的地址
            static: '<%=fc_cdn_static%>', //项目内静态资源库引用地址
            img: '<%=fc_cdn_image%>', //图片上传地址:引用后直接显示图片的前缀
            cdnVersion: '<%=CDN_VERSION%>' //版本
        };
        var zanke = (function() {
            /**
             *int.max.value
             */
            var maxValue = 2147483647;
            var weekStrZHArr = ['日', '一', '二', '三', '四', '五', '六', '日'];
            /**
             * [debug 打印控制台的日志]
             * @param  {[type]} key [description]
             * @param  {[type]} obj [description]
             * @return {[type]}     [description]
             */
            var debug = function(key, obj) {
                if (isDebug) {
                    if (obj) {
                        console.group(key + "  -  日志");
                        console.log(obj);
                        console.groupEnd();
                    } else {
                        console.info(key + "  -  日志");
                    }
                }
            };
            /**
             * [jqAjax]
             * @param  {[type]} url     [请求地址]
             * @param  {[type]} data    [传递参数]
             * @param  {[type]} success [成功的回调]
             * @param  {[type]} error   [错误的回调用]
             * @param  {[type]} type    [POST OR GET]
             * @param  {[type]} flage   [true:原样传递参数,false: json-->string,other-->原样传递]
             * @param  {[type]} loadId    [页面等待时的element的ID]
             * @return {[type]}         
             */
            var jqAjax = function(url, data, success, error, type, flage, loadId) {
                var obj = {
                    type: (type || 'POST'),
                    url: domain.base + url,
                    dataType: 'json',
                    async: true,
                    timeout: 0,
                    beforeSend: function() {
                        if (loadId) {
                            loadId = loadId.indexOf('#') >= 0 ? loadId : '#' + loadId;
                            zanke.block.blockUI(loadId);
                        }
                        debug("ajax[" + url + "] request param:", {
                            url: url,
                            data: data,
                            type: (type || 'POST'),
                            flage: (flage || false),
                            loadId: loadId
                        });
                    },
                    complete: function() {
                        if (loadId) {
                            loadId = loadId.indexOf('#') >= 0 ? loadId : '#' + loadId;
                            zanke.block.unblockUI(loadId);
                        }
                    },
                    success: function(json) {
                        if (json.result) {
                            if (success) {
                                debug("ajax[" + url + "] response result:", {
                                    url: url,
                                    data: data,
                                    type: (type || 'POST'),
                                    flage: (flage || false),
                                    loadId: loadId,
                                    res: json
                                });
                                success(json);
                            }
                        } else {
                            if (json.msg && json.msg.fieldError) {
                                message.error(json.msg.fieldError.join('<br>'));
                            } else {
                                message.error(json.msg);
                            }
                            if (error) {
                                error();
                            }
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        var sessionStatus = jqXHR.getResponseHeader('_zank_session_status');
                        if ('_timeout' == sessionStatus) {
                            window.location.href = domain.account + 'login.jsp';
                        } else {
                            if (jqXHR.status == 401) {
                                message.error("无权限执行此操作.");
                            } else {
                                var xhr = jqXHR;
                                if (xhr && xhr.responseText) {
                                    var _json = JSON.parse(xhr.responseText);
                                    if (!_json.res) {
                                        message.error(_json.msg);
                                    } else {
                                        message.error(xhr.responseText);
                                    }
                                } else {
                                    message.error("后端错误.");
                                }
                            }
                        }
                        if (error) {
                            error();
                        }
                    }
                };
                if (flage) {
                    if (data) {
                        obj.data = data;
                    }
                } else {
                    if ((typeof data) == 'object') {
                        obj.contentType = "application/json;charset=utf-8";
                        if (data) {
                            obj.data = JSON.stringify(data);
                        }
                    } else if ((typeof data) == 'string') {
                        if (data) {
                            obj.data = data;
                        }
                    }
                }
                $.ajax(obj);
            };
            /**
             * 转化成为时间格式的对象
             * @param  {[type]} date [description]
             * @return {[type]}      [description]
             */
            var newDate = function(date) {
                return new Date(Date.parse(date.replace(/-/g, "/")));
            };
            /**
             * 界面等待
             */
            var block = {
                blockUI: function(id) {
                    if (id) {
                        App.blockUI({
                            target: $(id)[0],
                            animate: true,
                            overlayColor: 'none',
                            isLoading: true
                        });
                    }
                },
                unblockUI: function(id) {
                    if (id) {
                        App.unblockUI($(id)[0]);
                    }
                }
            };
            /**
             * cookie 的操作
             */
            var cookie = {
                /**
                 * cookie 的设定
                 * @param  {String} c_name  cookie的名称
                 * @param  {String} value   cookie的值
                 * @param  {String} expiredays 过期天数
                 */
                set: function(c_name, value, expiredays) {
                    var exdate = new Date();
                    exdate.setDate(exdate.getDate() + expiredays);
                    document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString()) + ";path=/";
                }, //set end
                /**
                 * @param {string} c_name cookie的名称
                 * @returns {string}
                 */
                get: function(c_name) {
                        if (document.cookie.length > 0) {
                            c_start = document.cookie.indexOf(c_name + "=");
                            if (c_start != -1) {
                                c_start = c_start + c_name.length + 1;
                                c_end = document.cookie.indexOf(";", c_start);
                                if (c_end == -1) c_end = document.cookie.length;
                                return decodeURIComponent(document.cookie.substring(c_start, c_end));
                            }
                        }
                        return "";
                    } //get end
            };
            /**
             * 正则表达式
             */
            var pattern = {
                url: "^((https|http)://)" +
                    "(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                    +
                    "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                    +
                    "|" // 允许IP和DOMAIN（域名）
                    +
                    "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
                    +
                    "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
                    +
                    "[a-z]{2,6})" // first level domain- .com or .museum
                    +
                    "(:[0-9]{1,4})?" // 端口- :80
                    +
                    "((/?)|" // a slash isn't required if there is no file name
                    +
                    "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$",
                price: /^\d+(.\d{1,2})?$/,
                percentage: /^(100|[1-9]?\d(\.\d\d?)?)$/,
                number: /^[0-9]\d*?$/, //非零的判定自己额外增加
                phone: /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$|^(\(\d{3,4}\)|\d{3,4}-?|\s)?\d{7,8}$/, //座机和手机号同时验证
                //pwd: /^[a-zA-Z0-9]{8,20}$/, //密码的验证
                //pwd:/^(?![^a-z][^A-Z]+$)(?!\D+$).{10,50}$/, //密码的验证
                pwd:/^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{10,50}$/,
                mobile: /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/,
            };
            /**
             * 银行卡号验证
             * @param  {[type]} bankno [description]
             * @return {[type]}        [description]
             */
            var bankNoCheck = function(bankno) {
                if ($.trim(bankno) == "") {
                    return false;
                }
                var lastNum = bankno.substr(bankno.length - 1, 1); //取出最后一位（与luhm进行比较）

                var first15Num = bankno.substr(0, bankno.length - 1); //前15或18位
                var newArr = new Array();
                for (var i = first15Num.length - 1; i > -1; i--) { //前15或18位倒序存进数组
                    newArr.push(first15Num.substr(i, 1));
                }
                var arrJiShu = new Array(); //奇数位*2的积 <9
                var arrJiShu2 = new Array(); //奇数位*2的积 >9

                var arrOuShu = new Array(); //偶数位数组
                for (var j = 0; j < newArr.length; j++) {
                    if ((j + 1) % 2 == 1) { //奇数位
                        if (parseInt(newArr[j]) * 2 < 9)
                            arrJiShu.push(parseInt(newArr[j]) * 2);
                        else
                            arrJiShu2.push(parseInt(newArr[j]) * 2);
                    } else //偶数位
                        arrOuShu.push(newArr[j]);
                }

                var jishu_child1 = new Array(); //奇数位*2 >9 的分割之后的数组个位数
                var jishu_child2 = new Array(); //奇数位*2 >9 的分割之后的数组十位数
                for (var h = 0; h < arrJiShu2.length; h++) {
                    jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
                    jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
                }

                var sumJiShu = 0; //奇数位*2 < 9 的数组之和
                var sumOuShu = 0; //偶数位数组之和
                var sumJiShuChild1 = 0; //奇数位*2 >9 的分割之后的数组个位数之和
                var sumJiShuChild2 = 0; //奇数位*2 >9 的分割之后的数组十位数之和
                var sumTotal = 0;
                for (var m = 0; m < arrJiShu.length; m++) {
                    sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
                }

                for (var n = 0; n < arrOuShu.length; n++) {
                    sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
                }

                for (var p = 0; p < jishu_child1.length; p++) {
                    sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
                    sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
                }
                //计算总和
                sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu) + parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);

                //计算Luhm值
                var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
                var luhm = 10 - k;

                if (lastNum == luhm) {
                    return true;
                } else {
                    return false;
                }
            };

            var element = {
                pageY: function(El) {
                    var top = 0;
                    do {
                        top += El.offsetTop;
                    } while (El.offsetParent && (El = El.offsetParent).nodeName.toUpperCase() != 'BODY');
                    return top;
                },
                isShow: function(id, callback) {
                    (function(id, callback) {
                        id = id.indexOf('#') >= 0 ? id : ('#' + id);
                        var $curEle = $(id);
                        var curEle = $curEle[0];
                        var iScrollTop = document.documentElement.scrollTop || document.body.scrollTop;
                        var iClientHeight = document.documentElement.clientHeight + iScrollTop;

                        var iTop = element.pageY(curEle) - 200;
                        var iBottom = element.pageY(curEle) + curEle.offsetHeight + 200;

                        var isTopArea = (iTop > iScrollTop && iTop < iClientHeight) ? true : false;
                        var isBottomArea = (iBottom > iScrollTop && iBottom < iClientHeight) ? true : false;
                        if (isTopArea || isBottomArea) {
                            var isLoad = $curEle.attr('isLoad') || 0;
                            if (isLoad == 0) {
                                $curEle.attr('isLoad', '1');
                            }
                            if (callback) {
                                callback();
                            }
                        }
                    })(id, callback);
                }
            };

            var uniform = {
                initUniform: function(id) {
                    id = id.indexOf('#') >= 0 ? id : '#' + id;
                    if (id) {
                        var els = $(id);
                        if (els.parents(".checker").size() === 0) {
                            els.show();
                            els.uniform();
                        }
                    }
                },
                updateUniform: function(els) {
                    if (els) {
                        $.uniform.update(els);
                    } else {
                        $.uniform.update();
                    }
                }
            };

            return {
                jqAjax: jqAjax,
                constans: {
                    maxValue: maxValue,
                    weekStrZHArr: weekStrZHArr
                },
                debug: debug,
                newDate: newDate,
                block: {
                    blockUI: block.blockUI,
                    unblockUI: block.unblockUI
                },
                cookie: {
                    set: cookie.set,
                    get: cookie.get
                },
                test: {
                    url: function(val) {
                        return pattern.url.test(val);
                    },
                    price: function(val) {
                        return pattern.price.test(val);
                    },
                    percentage: function(val) {
                        return pattern.percentage.test(val);
                    },
                    number: function(val) {
                        return pattern.number.test(val);
                    },
                    goodsNo: function(val) {
                        return pattern.goodsNo.test(val);
                    },
                    phone: function(val) {
                        return pattern.phone.test(val);
                    },
                    pwd: function(val) {
                        return pattern.pwd.test(val);
                    },
                    mobile: function(val) {
                        return pattern.mobile.test(val);
                    },
                    bankNoCheck: bankNoCheck
                },
                element: {
                    isShow: element.isShow // //元素是否已经被看到
                },
                uniform: {
                    initUniform: uniform.initUniform,
                    updateUniform: uniform.updateUniform
                }
            };
        })();
        String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
            if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
                return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")), replaceWith);
            } else {
                return this.replace(reallyDo, replaceWith);
            }
        };
        /**
         * 全局执行操作
         */
        $.fn.datetimepicker.dates['zh'] = {
            days: ['日', '一', '二', '三', '四', '五', '六', '日'],
            daysShort: ['日', '一', '二', '三', '四', '五', '六', '日'],
            daysMin: ['日', '一', '二', '三', '四', '五', '六', '日'],
            months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            monthsShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
            today: "今天",
            meridiem: []
        };
    </script>