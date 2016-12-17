package com.gtriangle.admin.jcaptcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.jhlabs.image.WaterFilter;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.LineTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.gimpy.DefaultGimpyEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class ImageCodeEngine extends DefaultGimpyEngine {

	private static final String CODE = "一二三四五六七字符显示的个数";// I、0去掉"一二三四五六七字符显示的个数"  
	// 字符显示的个数  
	private static final Integer MIN_WORD_LEN = new Integer(4);  
	// 字符显示的个数  
	private static final Integer MAX_WORD_LEN = new Integer(4);  
	// 验证码图片的高度宽度设定  
	private static final Integer IMAGE_WIDTH = new Integer(100);  
	private static final Integer IMAGE_HEIGHT = new Integer(40);  
	// 验证码中显示的字体大小  
	private static final Integer MIN_FONT_SIZE = new Integer(24);  
	private static final Integer MAX_FONT_SIZE = new Integer(26);  

	protected void buildInitialFactories() {  
		WordGenerator wordGenerator = new RandomWordGenerator(CODE);  
		BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(  
				IMAGE_WIDTH, IMAGE_HEIGHT, Color.white, Color.white);  
		// 字体格式  
		Font[] fontsList = new Font[]{Font.decode("Arial"),Font.decode("Tahoma"),Font.decode("Verdana")};  
		fontsList = new Font[]{Font.decode("华文细黑")};//可以使用中文验证码，另外汉字宽度比较大，要重新调整一下字体大小,不然会出现异常  
		// 字体随机生成  
		FontGenerator fontGenerator = new RandomFontGenerator(MIN_FONT_SIZE,MAX_FONT_SIZE,fontsList);  
		// 背景颜色随机生成  
		// 验证码的颜色-使用随机颜色器new Integer[]{0,100},new Integer[]{0,100}, new  
		// Integer[]{0,100}  
		RandomRangeColorGenerator cgen = new RandomRangeColorGenerator(  
				new int[] { 0, 150 }, new int[] { 0, 150 },  
				new int[] { 0, 150 });  
		backgroundGenerator = new FunkyBackgroundGenerator(IMAGE_WIDTH, IMAGE_HEIGHT);  

		//文字干扰器--- 可以创建多个  
		BaffleTextDecorator baffleTextDecorator = new BaffleTextDecorator(2,cgen);//气泡干扰  
		LineTextDecorator lineTextDecorator = new LineTextDecorator(1,cgen);//曲线干扰  
		TextDecorator[] textDecorators = new TextDecorator[0];  
		//textDecorators[0] = baffleTextDecorator;  
		//textDecorators[0] = lineTextDecorator;  
		TextPaster textPaster = new DecoratedRandomTextPaster(MIN_WORD_LEN,MAX_WORD_LEN, cgen,true, new TextDecorator[] { new BaffleTextDecorator(new Integer(1), Color.WHITE) });   


//		//过滤器  
//		WaterFilter water = new WaterFilter();
//		water.setAmplitude(4d);//振幅  
//		water.setAntialias(true);//显示字会出现锯齿状,true就是平滑的  
//		//water.setPhase(30d);//月亮的盈亏   
//		water.setWavelength(60d);//  

		ImageDeformation backDef = new ImageDeformationByFilters(  
				new ImageFilter[]{});  
		ImageDeformation textDef = new ImageDeformationByFilters(  
				new ImageFilter[]{});  
		ImageDeformation postDef = new ImageDeformationByFilters(  
				new ImageFilter[]{});  
		// 生成图片输出  
		// WordToImage wordToImage = new ComposedWordToImage(fontGenerator,  
		// backgroundGenerator, textPaster);  
		// addFactory(new GimpyFactory(wordGenerator, wordToImage));  
		WordToImage word2image = new DeformedComposedWordToImage(fontGenerator, backgroundGenerator, textPaster,  
				backDef,  
				textDef,  
				postDef  
				);  
		addFactory(new GimpyFactory(wordGenerator, word2image));  
	}  
}
