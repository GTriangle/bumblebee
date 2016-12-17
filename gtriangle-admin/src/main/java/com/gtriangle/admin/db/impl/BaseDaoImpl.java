package com.gtriangle.admin.db.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import com.gtriangle.admin.db.IBaseDao;

/**
 * 
 * 此类描述的是：IBaseDao实现类
 * 
 * @author gaoyan
 */
public class BaseDaoImpl implements IBaseDao {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sst;
	

	/**
	 * 
	 * 此方法描述的是：
	 * 
	 * @param <T>
	 *            类模板
	 * @param statement
	 *            执行的sql
	 * @param clazz
	 *            返回的类
	 * @return T
	 */
	public <T> T findObj(String statement, Class<T> clazz) {
		return sst.selectOne(statement);
	}

	/**
	 * 根据statement语句查询，结果不为List，而是一个POJO对象。
	 * 
	 * @param <T>
	 *            类模板
	 * @param clazz
	 *            返回的类
	 * @param id
	 *            id
	 * @param statement
	 *            sql
	 * @return T
	 */
	public <T> T findObjById(String statement, String id, Class<T> clazz) {
		T ret = null;
		if (null == id) {
			ret = sst.selectOne(statement);
		} else {
			ret = sst.selectOne(statement, id);
		}
		return ret;
	}

	/**
	 * 根据statement语句查询，结果不为List，而是一个POJO对象。
	 * 
	 * @param <T>
	 *            类模板
	 * @param clazz
	 *            返回的类
	 * @param id
	 *            id
	 * @param statement
	 *            sql
	 * @return T
	 */
	public <T> T findObjById(String statement, Long id, Class<T> clazz) {
		T ret = null;
		if (null == id) {
			ret = sst.selectOne(statement);
		} else {
			ret = sst.selectOne(statement, id);
		}
		return ret;
	}

	/**
	 * 根据statement语句和parameter参数查询确定一个POJO对象。
	 * 
	 * @param <T>
	 *            类模板
	 * @param clazz
	 *            返回的类
	 * @param parameter
	 *            输入的参数
	 * @param statement
	 *            sql
	 * @return T
	 */
	public <T> T findObjByParameter(String statement, Object parameter, Class<T> clazz) {
		T ret = null;
		if (null == parameter) {
			ret = sst.selectOne(statement);
		} else {
			ret = sst.selectOne(statement, parameter);
		}

		return ret;
	}

	/**
	 * 根据statement语句查询，返回结果为所有记录。
	 * 
	 * @param <T>
	 *            类模板
	 * @param clazz
	 *            返回的类
	 * @param statement
	 *            sql
	 * @return T
	 */
	public <T> List<T> findAll(String statement, Class<T> clazz) {
		return sst.selectList(statement);
	}

	/**
	 * 根据statement语句和parameter参数查询，返回结果为符合条件的集合。
	 * 
	 * @param <T>
	 *            类模板
	 * @param clazz
	 *            返回的类
	 * @param parameter
	 *            输入的参数
	 * @param statement
	 *            sql
	 * @return List<T>
	 */
	public <T> List<T> findListByParameter(String statement, Object parameter, Class<T> clazz) {
		if (null == parameter) {
			return findAll(statement, clazz);
		} else {
			return sst.selectList(statement, parameter);
		}
	}

	/**
	 * 根据statement语句、parameter参数和page信息查询，返回结果为符合条件的集合。
	 * 
	 * @param <T>
	 *            类模板
	 * @param clazz
	 *            返回的类
	 * @param parameter
	 *            输入的参数
	 * @param statement
	 *            sql
	 * @return List<T>
	 */
	public <T> List<T> findPageListByParameter(String statement, Map<String, Object> parameter, int pageNo,
			int pageSize, Class<T> clazz) {
		if (null == parameter) {
			return findAll(statement, clazz);
		} else {
			parameter.put("start", (pageNo - 1) * pageSize);
			parameter.put("limit", pageSize);
			return sst.selectList(statement, parameter);
		}
	}

	/**
	 * 
	 * 
	 * @param parameter
	 *            参数
	 * @param statement
	 *            sql
	 * @return int
	 */
	public int findCount(String statement, Object parameter) {
		Object count = sst.selectOne(statement, parameter);
		return null == count ? 0 : (Integer) count;
	}

	/**
	 * 
	 * @param statement
	 *            sql
	 * @return int
	 */
	public int findCount(String statement) {
		Object count = sst.selectOne(statement);
		return null == count ? 0 : (Integer) count;
	}

	/**
	 * 
	 * 
	 * @param parameter
	 *            参数
	 * @param statement
	 *            sql
	 * @return int
	 */
	public int save(String statement, Object parameter) {
		return sst.insert(statement, parameter);
	}

	/**
	 * 
	 * 
	 * @param parameter
	 *            参数
	 * @param statement
	 *            sql
	 * @return int
	 */
	public int update(String statement, Object parameter) {
		return sst.update(statement, parameter);
	}

	/**
	 * 
	 * @param statement
	 *            sql
	 * @return int
	 */
	public int delete(String statement) {
		return sst.delete(statement);
	}

	/**
	 * 
	 * 
	 * @param parameter
	 *            参数
	 * @param statement
	 *            sql
	 * @return int
	 */
	public int delete(String statement, Object parameter) {
		return sst.delete(statement, parameter);
	}

	/**
	 * sst
	 * 
	 * @return the sst
	 */

	public SqlSessionTemplate getSst() {
		return sst;
	}

	/**
	 * @param sst
	 *            the sst to set
	 */

	public void setSst(SqlSessionTemplate sst) {
		this.sst = sst;
	}

	public static void main(String[] args) {
		while (true) {
			BaseDaoImpl.test3();
		}
	}

	public static void test2() {
		double total = 10;// 红包总额
		int num = 5;// 分成8个红包，支持8人随机领取
		int min = 1;// 每个人最少能收到0.01元
		Random fRandom = new Random();
		for (int i = 1; i < num; i++) {
			long safe_total = Math.round((total - (num - i) * min) / (num - i));// 随机安全上限
			long tmmm = safe_total - min;

			int money = (fRandom.nextInt(new Long(tmmm).intValue()) + min);

			total = total - money;
			System.out.print(money + " ");
		}
		System.out.println(Math.round(total));

	}

	public static void test4(){
		
		double total_money; // 红包总金额
		int total_people; // 抢红包总人数
		double min_money; // 每个人最少能收到0.01元

		total_money = 6;
		total_people = 5;
		min_money = 1;

		for (int i = 0; i < total_people - 1; i++) {
			int j = i + 1;
			double safe_money = (total_money - (total_people - j) * min_money)
					/ (total_people - j);
			double tmp_money = (Math.random()
					* (safe_money * 100 - min_money * 100) + min_money * 100) / 100;
			total_money = total_money - tmp_money;
			System.out.print(Math.round(tmp_money)+ " ");
		}
		System.out.println(Math.round(total_money)+ " ");
	}
		

	public static void test3() {
		double total_money = 16; // 红包总金额
		int total_people = 5; // 抢红包总人数
		double min_money = 1; // 每个人最少能收到0.01元
//		double total = 15;// 红包总额
//		int num = 5;// 分成8个红包，支持8人随机领取
//		int min = 1;// 每个人最少能收到0.01元
		Random fRandom = new Random();
		double total1 = total_money / 2;
		double total2 = total_money - total1;
		double[] value1 = new double[5];
		double[] value2 = new double[5];
		for (int i = 0; i < total_people - 1; i++) {
			int j = i + 1;
			double safe_money = (total1 - (total_people - j) * min_money)
					/ (total_people - j);
			double tmp_money = (Math.random()
					* (safe_money * 100 - min_money * 100) + min_money * 100) / 100;
			total1 = total1 - tmp_money;
			value1[i] = tmp_money;
		}
		value1[total_people - 1] = total1;
		for (int i = 0; i < total_people - 1; i++) {
			int j = i + 1;
			double safe_money = (total2 - (total_people - j) * min_money)
					/ (total_people - j);
			double tmp_money = (Math.random()
					* (safe_money * 100 - min_money * 100) + min_money * 100) / 100;
			total2 = total2 - tmp_money;
			value2[i] =tmp_money;
		}
		value2[total_people] = total2;
		double tmpMoney = 0;
		for (int i = 0; i < 4; i++) {
			System.out.print(Math.round(value1[i] + value2[4 - i]) + "   ");
			tmpMoney +=Math.round(value1[i] + value2[4 - i]);
		}
		System.out.println();
	}

	public static void test() {
		int j = 1;
		while (j < 1000) {
			int number = 5;
			float total = 20;
			float money;
			double min = 1;
			double max;
			int i = 1;

			List math = new ArrayList();
			while (i < number) {

				max = total - min * (number - i);
				int k = (int) ((number - i) / 2);
				if (number - i <= 2) {
					k = number - i;
				}
				max = max / k;
				money = (int) (min * 100 + Math.random() * (max * 100 - min * 100 + 1));
				money = (float) money / 100;
				total = total - money;
				math.add(money);
				System.out.println("第" + i + "个人拿到" + money + "剩下" + total);
				i++;
				if (i == number) {
					math.add(total);
					System.out.println("第" + i + "个人拿到" + total + "剩下0");
				}
			}

			System.out.println("本轮发红包中第" + (math.indexOf(Collections.max(math)) + 1) + "个人手气最佳");
			j++;
		}
	}

}
