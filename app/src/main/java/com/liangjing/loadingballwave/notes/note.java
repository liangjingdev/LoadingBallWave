package com.liangjing.loadingballwave.notes;

/**
 * Created by liangjing on 2017/7/19.
 * 功能：代码学习笔记
 */

public class note {

/**
 * （Paint使用）
 *  Typeface setTypeface (Typeface typeface)
 * 设置或清除typeface对象。
 * 传递null以清除任何先前的字体。为了方便，传递的参数也会返回。
 *参数
 *字体类型:可能为空。在油漆中安装的字体
 *返回:字体的类型
 *
 *
 * Typeface类指定字体的字体和固有样式。
 * 这是在油漆中使用的，还有可选择的油漆设置，如textSize、textexx、textScaleX，以指定绘制时文本显示的方式(以及度量)。
 *
 * DEFAULT_BOLD

  字体DEFAULT_BOLD
  默认的粗体字体对象。注意:这可能不是粗体，取决于安装了什么字体。调用getStyle()以确定无疑。


 MONOSPACE


 字体等宽字体
 默认的单空间字体的普通样式.

 SANS_SERIF

 字体SANS_SERIF
 默认的无衬线字体。

 SERIF

 字体衬线
 默认的衬线字体的正常样式。
 */

    /**
     *  (Paint)
     *  setTextAlign
        void setTextAlign (Paint.Align align)
        设置油漆的文本对齐。这就控制了文本相对于其起源的位置。左对齐意味着所有的文本都将被绘制到它的原点的右边(也就是源文件的左边缘)等等。
        参数
        对齐:为绘制文本设置油漆的对齐值。

        Paint.Align类
       将指定DrawText将其相对于[文本] X，Y坐标。默认为左。
       CENTER
       文本是在x，y原点上水平居中绘制的。
       LEFT
       文本被绘制到x，y原点的右边。
       RIGHT
      文本被画在x，y原点的左边。
     */

    /**
     * (Paint)
     * Paint.FontMetrics
     * 该类用于描述给定文本大小的字体的各种度量(印象笔记)
     */


    /**
     *  (Paint)
     *  setXfermode
        Xfermode setXfermode (Xfermode xfermode)
        设置或清除xfermode对象。
       通过清空以前的任何xfermode。作为方便一个，传递的参数也被返回。
     参数
     xfermode Xfermode：可能是空的。The xfermode to be installed in the paint
     返回
     xfermode xfermode


     (constructor)
     PorterDuffXfermode

     PorterDuffXfermode (PorterDuff.Mode mode)
     Create an xfermode that uses the specified porter-duff mode.

     参数
     mode	PorterDuff.Mode: The porter-duff mode that is applied

     */
}
