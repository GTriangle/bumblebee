package com.gtriangle.admin.jcaptcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.jhlabs.image.WaterFilter;
import com.octo.captcha.component.image.backgroundgenerator.AbstractBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.LineTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class ImageCaptchaEngineExtend extends ListImageCaptchaEngine {

	@Override
	protected void buildInitialFactories() {
		 // build filters  
		   // build filters 波浪实现类  
        WaterFilter water = new WaterFilter();  
        int fontSize = 24;
        water.setAmplitude(3d);  
        water.setAntialias(true);  
        water.setPhase(30d);  
        water.setWavelength(80d);  
  
        ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});  
        ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});  
        ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {  });  
  
        // word generator  
        WordGenerator dictionnaryWords = new RandomWordGenerator("abcdefhjkmnprstuvwxyz23456789");  
        // wordtoimage components  
  
        int[] R = new int[] { 0, 51 };  
        int[] G = new int[] { 0, 51 };  
        int[] B = new int[] { 0, 51 };  
  
        RandomRangeColorGenerator colors = new RandomRangeColorGenerator(R, G, B);  
  
        // Arial,Tahoma,Verdana,Helvetica,宋体,黑体,幼圆, 字体大小  
        Font[] fonts = new Font[] { new Font("Arial", 0, fontSize), new Font("Tahoma", 0, fontSize), new Font("Verdana", 0, fontSize),  
                new Font("Helvetica", 0, fontSize), new Font("宋体", 0, fontSize), new Font("黑体", 0, fontSize), new Font("幼圆", 0, fontSize) };  
  
        // 设置字符以及干扰线颜色  
        RandomRangeColorGenerator lineColors = new RandomRangeColorGenerator(R, G, B);  
  
        // 添加干扰线(可选取圆点干扰实现类BaffleTextDecorator LineTextDecorator)  
        TextPaster randomPaster = new DecoratedRandomTextPaster(4, 4, colors, true,  
                new TextDecorator[] { new LineTextDecorator(1, lineColors) }); 
        
//        TextPaster randomPaster = new DecoratedRandomTextPaster(4,
//                4, new RandomListColorGenerator(new Color[]{
//                new Color(23, 170, 27), new Color(220, 34, 11),
//                new Color(23, 67, 172)}), new TextDecorator[]{});
  
        RandomRangeColorGenerator backColorGenerator = new RandomRangeColorGenerator(new int[] { 75, 255 }, new int[] {  
                75, 255 }, new int[] { 75, 255 });  
  
        // 背景描绘  
        BackgroundGenerator back = new GradientBackgroundGenerator(140, 50,Color.white,Color.white);  
        //AbstractBackgroundGenerator back = new FunkyBackgroundGenerator(140, 50, backColorGenerator);  
  
        FontGenerator shearedFont = new RandomFontGenerator(35, 0, fonts);  
        // word2image 1  
        WordToImage word2image = new DeformedComposedWordToImage(shearedFont, back, randomPaster, backDef, textDef,  
                postDef);  
        // 输入图片  
        this.addFactory(new GimpyFactory(dictionnaryWords, word2image));  

	}

}
