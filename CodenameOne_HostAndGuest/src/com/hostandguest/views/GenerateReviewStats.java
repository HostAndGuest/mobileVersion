/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hostandguest.views;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.BarChart;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.hostandguest.entities.Review;
import com.hostandguest.util.NoOpenReviewReservationsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Pair;

/**
 *
 * @author Unlucky
 */
public class GenerateReviewStats {
    protected Form formStats;
    
    public GenerateReviewStats(List<Review> reviews, Form formList) {
        formStats = new Form();
        List<List<Integer>> values = new ArrayList<>();
        
        List<Integer> pqRates = new ArrayList<>();
        List<Integer> posRates = new ArrayList<>();
        List<Integer> precisionRates = new ArrayList<>();
        List<Integer> comRates = new ArrayList<>();
        List<Integer> cleanRates = new ArrayList<>();
                
        Set<Integer> xLabels = getXLabels(reviews);
        String[] titles = new String[]{"Price Quality", "Position", "Precision", "Communication", "Cleanliness"};
        
        int[] colors = new int[]{ColorUtil.rgb(254, 197, 28), ColorUtil.LTGRAY, ColorUtil.rgb(185, 240, 234), ColorUtil.rgb(255, 184, 162), ColorUtil.rgb(7, 123, 139)};
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        setChartSettings(renderer, "Review Rating Stats Per Year", "Year", "Rating", 0.5,
                xLabels.size() + 0.5, 0, 5, ColorUtil.GRAY, ColorUtil.LTGRAY);
        renderer.setXLabels(1);
        renderer.setYLabels(10);
       
        int count = 1;
        for (Integer year : xLabels)
        {
            renderer.addXTextLabel(count++, String.valueOf(year));
            // each value in the returned list from generateStats is an individual Rate
            // represents the average rates of the given year
            List<Integer> avgRates = generateStats(reviews, year);
            
            pqRates.add(avgRates.get(0));
            posRates.add(avgRates.get(1));
            precisionRates.add(avgRates.get(2));
            comRates.add(avgRates.get(3));
            cleanRates.add(avgRates.get(4));
        }
        
        values.add(pqRates);
        values.add(posRates);
        values.add(precisionRates);
        values.add(comRates);
        values.add(cleanRates);
        
        initRendererer(renderer);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
            seriesRenderer.setDisplayChartValues(true);
        }

        BarChart chart = new BarChart(buildBarDataset(titles, values), renderer,
                BarChart.Type.DEFAULT);
        
        ChartComponent c = new ChartComponent(chart);
        c.setFocusable(true);
        c.setZoomEnabled(true);
        c.setPanEnabled(false);
        
        // setting go back command
        Style s = UIManager.getInstance().getComponentStyle("Title");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_ASSIGNMENT_RETURN, s);
        Command goBackCmd = new Command("", icon) {
            @Override
            public void actionPerformed(ActionEvent evt) {
                formList.showBack();
            }  
        };
        
        formStats.getToolbar().addCommandToRightBar(goBackCmd);
        formStats.add(c);
    }

    public Form getFormStats() {
        return formStats;
    }
    
    private XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
        Font smallFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_SMALL, Font.STYLE_PLAIN);
        
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(smallFont.getHeight() / 2);
        renderer.setChartTitleTextFont(smallFont);
        renderer.setLabelsTextSize(smallFont.getHeight() / 2);
        renderer.setLegendTextSize(smallFont.getHeight() / 2);
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }
    
    private XYMultipleSeriesDataset buildBarDataset(String[] titles, List<List<Integer>> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            CategorySeries series = new CategorySeries(titles[i]);
            List<Integer> v = values.get(i);
            int seriesLength = v.size();
            for (int k = 0; k < seriesLength; k++) {
                series.add(v.get(k));
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }
    
    private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
            String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
            int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }
    
    private void initRendererer(DefaultRenderer renderer) {
        renderer.setBackgroundColor(0xffffffff);
        renderer.setApplyBackgroundColor(true);
        renderer.setLabelsColor(0xff000000);
        renderer.setAxesColor(0xff000000);
        if(Font.isNativeFontSchemeSupported()) {
            Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                    derive(Display.getInstance().convertToPixels(2.5f), Font.STYLE_PLAIN);
            renderer.setTextTypeface(fnt);
            renderer.setChartTitleTextFont(fnt);
            renderer.setLabelsTextFont(fnt);
            renderer.setLegendTextFont(fnt);

            if(renderer instanceof XYMultipleSeriesRenderer) {
                ((XYMultipleSeriesRenderer)renderer).setAxisTitleTextFont(fnt);
            }
            if(renderer instanceof XYMultipleSeriesRenderer) {
                XYMultipleSeriesRenderer x = (XYMultipleSeriesRenderer)renderer;
                x.setMarginsColor(0xfff7f7f7);
                x.setXLabelsColor(0xff000000);
                x.setYLabelsColor(0, 0xff000000);
            }
        }
    }

    /**
     * generate all different stats by extracting and grouping by year
     * 1st item in the list is the BarChart X titles which represents the years range
     * @param reviews
     */
    private List<Integer> generateStats(List<Review> reviews, int year) {
        List<Integer> pqRates = new ArrayList<>();
        List<Integer> posRates = new ArrayList<>();
        List<Integer> precisionRates = new ArrayList<>();
        List<Integer> comRates = new ArrayList<>();
        List<Integer> cleanRates = new ArrayList<>();
        
        for (Review review : reviews)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(review.getDateComment());
            
            if (cal.get(Calendar.YEAR) == year)
            {
                pqRates.add(review.getPrice_quality());
                posRates.add(review.getLieu());
                precisionRates.add(review.getPrecision());
                comRates.add(review.getCommunication());
                cleanRates.add(review.getCleanliness());
            }
        }
        
        int sumPQ = 0, sumPre = 0, sumPos = 0, sumCom = 0, sumClean = 0, size = pqRates.size();
        
        // all lists have the same size
        for (int count = 0; count < size; count++)
        {
            sumPQ += pqRates.get(count);
            sumPre += precisionRates.get(count);
            sumPos += posRates.get(count);
            sumCom += comRates.get(count);
            sumClean += cleanRates.get(count);
        }
        
        // returning avg
        return Arrays.asList(sumPQ / size, sumPre / size, sumPos / size, sumCom / size, sumClean / size);
    }

    private Set<Integer> getXLabels(List<Review> reviews) {
        // use of hashSet to not have to deal with duplicates making it a more proper data structure
        Set<Integer> years = new HashSet<>();
        
        for (Review review : reviews)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(review.getDateComment());
            years.add(cal.get(Calendar.YEAR));
        }
        
        return years;
    }
}
