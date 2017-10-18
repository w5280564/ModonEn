package lecho.lib.hellocharts.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;

import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;

/**
 * Abstract renderer implementation, every chart renderer extends this class(although it is not required it helps).
 */
public abstract class AbstractChartRenderer implements ChartRenderer {
    public int DEFAULT_LABEL_MARGIN_DP = 4;
    protected Chart chart;
    protected ChartComputator computator;
    /**
     * Paint for value labels.
     */
    protected Paint labelPaint = new Paint();

    /**
     * Paint for value text.
     */
    protected Paint textPaint = new Paint();
    /**
     * Paint for labels background.
     */
    protected Paint labelBackgroundPaint = new Paint();
    /**
     * Holds coordinates for label background rect.
     */
    protected RectF labelBackgroundRect = new RectF();
    /**
     * Font metrics for label paint, used to determine text height.
     */
    protected FontMetricsInt fontMetrics = new FontMetricsInt();
    /**
     * If true maximum and current viewport will be calculated when chart data change or during data animations.
     */
    protected boolean isViewportCalculationEnabled = true;
    protected float density;
    protected float scaledDensity;
    protected SelectedValue selectedValue = new SelectedValue();
    protected char[] labelBuffer = new char[64];
    protected int labelOffset;
    protected int labelMargin;
    protected boolean isValueLabelBackgroundEnabled;
    protected boolean isValueLabelBackgroundAuto;

    public AbstractChartRenderer(Context context, Chart chart) {
        this.density = context.getResources().getDisplayMetrics().density;
        this.scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        this.chart = chart;
        this.computator = chart.getChartComputator();

        labelMargin = ChartUtils.dp2px(density, DEFAULT_LABEL_MARGIN_DP);
        labelOffset = labelMargin;

        labelPaint.setAntiAlias(true);
        labelPaint.setStyle(Paint.Style.FILL);
        labelPaint.setTextAlign(Align.LEFT);
        labelPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        labelPaint.setColor(Color.WHITE);

        textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        labelBackgroundPaint.setAntiAlias(true);
        labelBackgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void resetRenderer() {
        this.computator = chart.getChartComputator();
    }

    @Override
    public void onChartDataChanged() {
        final ChartData data = chart.getChartData();

        Typeface typeface = chart.getChartData().getValueLabelTypeface();
        if (null != typeface) {
            labelPaint.setTypeface(typeface);
        }

        labelPaint.setColor(data.getValueLabelTextColor());
        labelPaint.setTextSize((float) (ChartUtils.sp2px(scaledDensity, data.getValueLabelTextSize()) * 1.5));

        labelPaint.getFontMetricsInt(fontMetrics);

        textPaint.setTextSize(ChartUtils.sp2px(scaledDensity, data.getValueLabelTextSize()));
        this.isValueLabelBackgroundEnabled = data.isValueLabelBackgroundEnabled();
        this.isValueLabelBackgroundAuto = data.isValueLabelBackgroundAuto();
        this.labelBackgroundPaint.setColor(data.getValueLabelBackgroundColor());

        // Important - clear selection when data changed.
        selectedValue.clear();

    }

    /**
     * Draws label text and label background if isValueLabelBackgroundEnabled is true.
     */
    protected void drawLabelTextAndBackground(Canvas canvas, char[] labelBuffer, int startIndex, int numChars, int autoBackgroundColor
            , int status
    ) {
//        float trianStartX,float endStartX,float pathY,float pathCenterX,float pathCenterY
        final float textX;
        final float textY;

        if (isValueLabelBackgroundEnabled) {

            if (isValueLabelBackgroundAuto) {
                labelBackgroundPaint.setColor(autoBackgroundColor);
            }


            Path path = new Path();
            if (status == 0) {
                labelBackgroundRect.top = labelBackgroundRect.top - 10;
                labelBackgroundRect.bottom = labelBackgroundRect.bottom - 10;

                path.moveTo(labelBackgroundRect.left + 10, labelBackgroundRect.bottom - 1);
                path.lineTo(labelBackgroundRect.centerX(), labelBackgroundRect.centerY() + 50);
                path.lineTo(labelBackgroundRect.right - 10, labelBackgroundRect.bottom - 1);
                Log.e("label", labelBackgroundRect.toString());
                path.close(); //闭环
                canvas.drawPath(path, labelBackgroundPaint);

            } else if (status == 1) {
                labelBackgroundRect.top = labelBackgroundRect.top + 10;
                labelBackgroundRect.bottom = labelBackgroundRect.bottom + 10;

                path.moveTo(labelBackgroundRect.left + 10, labelBackgroundRect.top + 1);
                path.lineTo(labelBackgroundRect.centerX(), labelBackgroundRect.centerY() - 50);
                path.lineTo(labelBackgroundRect.right - 10, labelBackgroundRect.top + 1);
                Log.e("label", labelBackgroundRect.toString());
                path.close(); //闭环
                canvas.drawPath(path, labelBackgroundPaint);


            }

//            canvas.drawRect(labelBackgroundRect, labelBackgroundPaint);//画背景
//            labelBackgroundRect.top = labelBackgroundRect.top - 50;
//            labelBackgroundRect.bottom = labelBackgroundRect.bottom - 50;
            canvas.drawRoundRect(labelBackgroundRect, 10, 10, labelBackgroundPaint);
            //画三角形（这里是基于path路径的绘制）
            // dPaint);


            textX = labelBackgroundRect.left + labelMargin;
            textY = labelBackgroundRect.bottom - labelMargin;
        } else {
            textX = labelBackgroundRect.left;
            textY = labelBackgroundRect.bottom;
        }
        labelPaint.setColor(Color.WHITE);//节点显示数据颜色

        canvas.drawText(labelBuffer, startIndex, numChars, textX, textY, labelPaint);
    }

    protected void drawLabelText(Canvas canvas, char[] labelBuffer, int startIndex, int numChars, int autoBackgroundColor) {
        final float textX;
        final float textY;

        if (isValueLabelBackgroundEnabled) {

            if (isValueLabelBackgroundAuto) {
                labelBackgroundPaint.setColor(autoBackgroundColor);
            }
//            canvas.drawRect(labelBackgroundRect, labelBackgroundPaint);//画背景
            textX = labelBackgroundRect.left + labelMargin;
            textY = labelBackgroundRect.bottom - labelMargin;
        } else {
            textX = labelBackgroundRect.left;
            textY = labelBackgroundRect.bottom;
        }
        labelPaint.setColor(Color.BLACK);//节点显示数据颜色

        canvas.drawText(labelBuffer, startIndex, numChars, textX, textY, textPaint);
    }

    @Override
    public boolean isTouched() {
        return selectedValue.isSet();
    }

    @Override
    public void clearTouch() {
        selectedValue.clear();
    }

    @Override
    public Viewport getMaximumViewport() {
        return computator.getMaximumViewport();
    }

    @Override
    public void setMaximumViewport(Viewport maxViewport) {
        if (null != maxViewport) {
            computator.setMaxViewport(maxViewport);
        }
    }

    @Override
    public Viewport getCurrentViewport() {
        return computator.getCurrentViewport();
    }

    @Override
    public void setCurrentViewport(Viewport viewport) {
        if (null != viewport) {
            computator.setCurrentViewport(viewport);
        }
    }

    @Override
    public boolean isViewportCalculationEnabled() {
        return isViewportCalculationEnabled;
    }

    @Override
    public void setViewportCalculationEnabled(boolean isEnabled) {
        this.isViewportCalculationEnabled = isEnabled;
    }

    @Override
    public void selectValue(SelectedValue selectedValue) {
        this.selectedValue.set(selectedValue);
    }

    @Override
    public SelectedValue getSelectedValue() {
        return selectedValue;
    }
}