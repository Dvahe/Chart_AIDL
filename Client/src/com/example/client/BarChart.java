package com.example.client;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.Log;

public class BarChart {

	private GraphicalView view;
	private TimeSeries dataset; 
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	private XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // Holds a collection of XYSeriesRenderer and customizes the graph
	private int[] y = new int[15];
	
	public BarChart()
	{	
		dataset = new TimeSeries("");
		for(int i = 0; i < y.length; ++i){
			dataset.add(i, y[i]);
		}
		// Add single dataset to multiple dataset
		mDataset.addSeries(dataset);
		renderer.setColor(Color.BLUE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		renderer.setDisplayChartValues(true);
		renderer.setLineWidth(0);
		renderer.setChartValuesTextSize(20);
		renderer.setFillBelowLine(true);
		
		/***
		* Customizing graphs
		*/
		//setting text size of the title
		mRenderer.setChartTitleTextSize(28);
		//setting text size of the axis title
		mRenderer.setAxisTitleTextSize(24);
		//setting text size of the graph lable
		mRenderer.setLabelsTextSize(24);
		//setting zoom buttons visiblity
		mRenderer.setZoomButtonsVisible(false);
		//setting pan enablity which uses graph to move on both axis
		mRenderer.setPanEnabled(false, false);
		//setting click false on graph
		mRenderer.setClickEnabled(false);
		//setting zoom to false on both axis
		mRenderer.setZoomEnabled(false, false);
		//setting lines to display on y axis
		mRenderer.setShowGridY(true);
		//setting lines to display on x axis
		mRenderer.setShowGridX(true);
		//setting legend to fit the screen size
		mRenderer.setFitLegend(true);
		//setting displaying line on grid
		mRenderer.setShowGrid(true);
		//setting zoom to false
		mRenderer.setZoomEnabled(false);
		//setting external zoom functions to false
		mRenderer.setExternalZoomEnabled(false);
		//setting displaying lines on graph to be formatted(like using graphics)
		mRenderer.setAntialiasing(true);
		//setting to set legend height of the graph
		mRenderer.setLegendHeight(30);
		//setting x axis label align
		mRenderer.setXLabelsAlign(Align.CENTER);
		//setting y axis label to align
		mRenderer.setYLabelsAlign(Align.LEFT);
		//setting text style
		mRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
		//setting no of values to display in y axis
		mRenderer.setYLabels(10);
		//setting no of values to display in y axis
		mRenderer.setXLabels(15);
		//setting used to move the graph on xaxiz to .5 to the right
		mRenderer.setXAxisMin(0.5);
		//setting used to move the graph on xaxiz to .5 to the right
		mRenderer.setXAxisMax(11);
		//setting bar size or space between two bars
		mRenderer.setBarSpacing(1);
		//setting used to move the graph on xaxiz to .5 to the right
		mRenderer.setXAxisMin(-0.5);
		//Setting background color of the graph to transparent
		mRenderer.setBackgroundColor(Color.TRANSPARENT);
		//Setting margin color of the graph to transparent
		//mRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setScale(2f);
		//setting x axis point size
		mRenderer.setPointSize(4f);
		//setting the margin size for the graph in the order top, left, bottom, right
		mRenderer.setMargins(new int[]{30, 30, 30, 30});
		//set Title X
		mRenderer.setXTitle("#");
		//setTitle Y
		mRenderer.setYTitle("# Count of calls");
		mRenderer.setYAxisMax(10);
		mRenderer.setYAxisMin(0);
		mRenderer.setXAxisMax(15);
		mRenderer.setXAxisMin(0);
		mRenderer.setGridColor(Color.BLACK);
		mRenderer.addSeriesRenderer(renderer);	
	}
	
	public GraphicalView getView(Context context) 
	{
		view = ChartFactory.getBarChartView(context, mDataset, mRenderer, Type.STACKED);
		return view;
	}
	
	public void addNewPoints( int z)
	{		
		int index = z/20;
		Log.i("index", "index:  "  + index);
		y[index] += 1;
		dataset.add(index, y[index] );
    }
	
	public void resetBChart(){
		Log.i("index", "length" + y.length);
		for(int i = 0; i < y.length; ++i){
			y[i] = 0;
			dataset.add(i, y[i]);
		}
	}
}
	
