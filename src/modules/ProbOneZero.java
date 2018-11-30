package modules;

import graphs.Chart;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import sequence.Sequence;

public class ProbOneZero extends AbstractModule {
	
	private ArrayList<Double> pOne = new ArrayList<Double>();
	private ArrayList<Double> pZero = new ArrayList<Double>();
	
	private ArrayList<Double> avO = new ArrayList<Double>();
	private ArrayList<Double> avZ = new ArrayList<Double>();
	
	private ArrayList<Double> med0 = new ArrayList<Double>();
	private ArrayList<Double> med1 = new ArrayList<Double>();
	
	private ArrayList<Integer> buf = new ArrayList<Integer>();
	private ArrayList<Integer> steps = new ArrayList<Integer>();
	
	private ArrayList<Double> maxArr = new ArrayList<Double>();
	private ArrayList<Double> minArr = new ArrayList<Double>();
	
	public ProbOneZero (){
		
	}
		
	public void processSequence(Sequence sq){
		
		String s = sq.getFileName().replaceAll(".bin", "");
		ArrayList<Integer> sequence = sq.getSequence();
		
		System.out.println("SQ SIZE: "+sequence.size());
		int size = sq.getSequence().size();
		size = (int)(Math.log(size) / Math.log(2.0));
		System.out.println("log2 = "+size);
		int minStep = 1;//1024;
		
		long start, end;
		start = System.nanoTime();
		
		for(int i = minStep; i<(int)Math.pow(2, size); i=i+minStep ){
			steps.add(i);			//((int)Math.pow(2, i));
		}
		//System.out.println("ARR pow(2): "+steps);
		
		for(int k=0; k < steps.size(); k++){//for(int k=0; k < size.length; k++){
	    	int N = steps.get(k);//size[k];
	    	int bufSize = 0;
	    	if( (sequence.size() % N) != 0){
	    		bufSize = sequence.size() % N;
	    	}
			for(int i=0; i< sequence.size()-bufSize; i=i+N){

				for(int j=0,c=i;j<N;j++,c++){
					buf.add(sequence.get(c));
				}
				
				
				probability(buf);
				//pOne.add(probOne(buf));
				//pZero.add(probZero(buf));

				buf.clear();
			}
			
			System.out.println("SIZE: "+pOne.size());
			
			//String name2 = dialog.MainWindow.getSaveFile()+ s+"_P_"+N;
			//writeDataPFile(name2, pOne, pZero);
			//writePictureFile(name2);
			
			med0.add(median(pZero));
			med1.add(median(pOne));
			
			avO.add(average(pOne));
			avZ.add(average(pZero));
			
			maxArr.remove(maxArr.size()-1);
			minArr.remove(minArr.size()-1);
			
			pOne.clear();
			pZero.clear();
			
	    }
		
		String name3 = dialog.MainWindow.getSaveFile()+s+"_average_P";
		writeDataPFile(name3, avO, avZ);
		//writePictureFile(name3);
		
		String name4 = dialog.MainWindow.getSaveFile()+s+"_median_P";
		writeDataPFile(name4, med1, med0);
		//writePictureFile(name4);
		
		String name5 = dialog.MainWindow.getSaveFile()+s+"_MinMax_P";
		writeMinMaxFile(name5, maxArr, minArr);
		//writePictureFile(name5);
		
		try {
			drawPicture(name3, avO, avZ);
			drawPicture(name4, med1, med0);
			drawPictureMinMax(name5, maxArr, minArr);
		} catch (IOException e) { e.printStackTrace(); }
		
		
		reset();
		end = System.nanoTime();
		System.out.println("TIME: " + (double)(end - start) / 1000000000.0);
	}
	
	public void probability (ArrayList<Integer> test){
		double one=0, Po=0, zero=0, Pz=0;
		for(int i =0; i<test.size(); i++){
			if(test.get(i)==1)
				one++;
			if(test.get(i)==0)
				zero++;
		}

		Po=one/test.size();
		Pz=zero/test.size();
		pOne.add(Po);
		pZero.add(Pz);
	}
	
	public double probOne (ArrayList<Integer> test0){
		double one=0, Po=0;
		for(int i =0; i<test0.size(); i++){
			if(test0.get(i)==1)
				one++;
		}

		Po=one/test0.size();
		return Po;
	}
	
	public double probZero (ArrayList<Integer> test0){
		double zero=0, Pz=0;
		for(int i =0; i<test0.size(); i++){
			if(test0.get(i)==0)
				zero++;
		}

		Pz=zero/test0.size();
		return Pz;
	}

	public double average (ArrayList<Double> sq){
		
		double Sum=0, mid=0;
		double max = 0.0;
		double min = 1.0;
		
		for(int i =0; i<sq.size(); i++){
			Sum = Sum + sq.get(i);
			if ((Math.abs( (sq.get(i)-0.5)) > max) /*&& (sq.get(i) >= 0.5)*/)
				max = Math.abs( (sq.get(i)-0.5));
			if ((Math.abs( (sq.get(i)-0.5)) < min) /*&& (sq.get(i) >= 0.5)*/)
				min = Math.abs( (sq.get(i)-0.5));
		}

		maxArr.add(max);
		minArr.add(min);
		mid=Sum/sq.size();
		return mid;
	}
	
	public void getMaxMin(ArrayList<Double> sq) {
		double max = 0.0;
		double min = 1.0;
		
		for (int i = 0; i < sq.size(); i++) {
			if (sq.get(i) > max)
				max = sq.get(i);
			if (sq.get(i) < min)
				min = sq.get(i);
		}
		maxArr.add(max);
		minArr.add(min);

	}
	
	public double median (ArrayList<Double> sq){
		double med=0.0;
		QuickSort sort = new QuickSort();
		sort.quicksort(sq);
		if (sq.size() % 2 == 0)
		    med = (sq.get(sq.size()/2) + sq.get(sq.size()/2-1))/2;
		else
		    med = sq.get(sq.size()/2);
		
		return med;
	}
	
    public void writeDataPFile(String name, ArrayList<Double> pOne, ArrayList<Double> pZero){
    	
		try(FileWriter writer = new FileWriter(name, false))
        {
			for(int i=0;i<pOne.size();i++){
	            String str = i+" " + String.valueOf(pOne.get(i)) + " " + String.valueOf(pZero.get(i));
				writer.write(str);
				writer.append('\n');
			}
             
            writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        }
	
    }
    
    public void writeMinMaxFile(String name, ArrayList<Double> pOne, ArrayList<Double> pZero){
    	
		try(FileWriter writer = new FileWriter(name, false))
        {
			for(int i=pOne.size()-1;i>=0;i--){
	            String str = i+" " + String.valueOf(pOne.get(i)) + " " + String.valueOf(pZero.get(i));
				writer.write(str);
				writer.append('\n');
			}
             
            writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        }
	
    }
    
    public void writePictureFile(String name){
    	
    	try(FileWriter writer = new FileWriter("call2.plt", false))
        {
	        String str = "call 'plotData2' '" + name + "_picture'" + " '" + name+"'";
	        writer.write(str);
			writer.append('\n');
             
            writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        }
		
		try {
			Runtime.getRuntime().exec("gnuplot call2.plt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
    
    public void drawPictureMinMax(String name, ArrayList<Double> pOne, ArrayList<Double> pZero) throws IOException{
		
		// Create Chart
		XYChart chart = new XYChartBuilder().width(600).height(370).title(name).build();

		// Customize Chart
		chart.getStyler().setChartBackgroundColor(Color.WHITE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
		chart.getStyler().setChartTitleVisible(true);
		chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
		chart.getStyler().setMarkerSize(0);
		//chart.getStyler().setYAxisMin(0.4);
		//chart.getStyler().setYAxisMax(0.6);
		//chart.getStyler().setXAxisMin((double)pOne.size());
		chart.getStyler().setXAxisMax(1400.0);

		// Series
		ArrayList<Double> x = new ArrayList<Double>();
		for(double i=0; i<pOne.size(); i++)
			x.add(i+1);
			
		XYSeries series1 = chart.addSeries("Max", x, pOne);
		series1.setLineWidth(1);
		XYSeries series2 = chart.addSeries("Min", x, pZero);
		series2.setLineWidth(1);

		//new SwingWrapper(chart).displayChart();
		 BitmapEncoder.saveBitmap(chart, name, BitmapFormat.JPG);
	}
    
public void drawPicture(String name, ArrayList<Double> pOne, ArrayList<Double> pZero) throws IOException{
		
		// Create Chart
		XYChart chart = new XYChartBuilder().width(600).height(370).title(name).build();

		// Customize Chart
		chart.getStyler().setChartBackgroundColor(Color.WHITE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
		chart.getStyler().setChartTitleVisible(true);
		chart.getStyler().setLegendPosition(LegendPosition.InsideNE);
		chart.getStyler().setMarkerSize(0);

		// Series
		ArrayList<Double> x = new ArrayList<Double>();
		for(double i=0; i<pOne.size(); i++)
			x.add(i+1);
			
		XYSeries series1 = chart.addSeries("1", x, pOne);
		series1.setLineWidth(1);
		XYSeries series2 = chart.addSeries("0", x, pZero);
		series2.setLineWidth(1);

		//new SwingWrapper(chart).displayChart();
		 BitmapEncoder.saveBitmap(chart, name, BitmapFormat.JPG);
	}
    
    public void reset(){
    	avO.clear();
		avZ.clear();
		med1.clear();
		med0.clear();
		maxArr.clear();
		minArr.clear();
		//steps.clear();
	}

    public boolean ignoreInReport() {
		return false;
	}

    public JPanel getResultsPanel() {
    	return new Chart("Probability");
	}
    
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}
}
