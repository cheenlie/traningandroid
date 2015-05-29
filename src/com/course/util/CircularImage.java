package com.course.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * 圆形头像具体类
 */
//抽象函数中的抽象函数相当于接口，必须实现

public class CircularImage extends MaskedImage {
	
	
	public CircularImage(Context paramContext) {
		super(paramContext);
	}
	
	public CircularImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public CircularImage(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	public Bitmap createMask() {
		int i=getWidth();
		int j=getHeight();
		Bitmap.Config localConfig=Bitmap.Config.ARGB_8888;
		Bitmap locBitmap=Bitmap.createBitmap(i,j,localConfig);
		Canvas loCanvas=new Canvas(locBitmap);
		
		Paint loPaint=new Paint(1);
		loPaint.setColor(-16777216);
		
		float f1=getWidth();
		float f2=getHeight();
		RectF lorRectF=new RectF(0.0F,0.0F,f1,f2);
		
		loCanvas.drawOval(lorRectF, loPaint);
		return locBitmap;
	}
	

}
