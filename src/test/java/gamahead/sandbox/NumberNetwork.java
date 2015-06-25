package gamahead.sandbox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;

import org.numenta.nupic.Parameters;
import org.numenta.nupic.Parameters.*;
import org.numenta.nupic.network.*;
import org.numenta.nupic.datagen.*;
import org.numenta.nupic.examples.network.*;
import org.numenta.nupic.algorithms.*;
import org.numenta.nupic.network.sensor.*;
import org.numenta.nupic.network.sensor.SensorParams.Keys;
import org.numenta.nupic.research.*;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

public class NumberNetwork {

	public static void main(String[] args) {
		
		Random r = new Random();
		
		Parameters p = Parameters.getAllDefaultParameters();
//		p.setParameterByKey(KEY.ENCODER, "PassThroughEncoder");
		p.setParameterByKey(KEY.INPUT_DIMENSIONS, new int[] {884});
		p.setParameterByKey(KEY.TM_VERBOSITY, 9);
//		p.setParameterByKey(KEY.N, 9);
//		p.setParameterByKey(KEY.W, 9);
				
		Network network = Network.create("Network API Demo", p)
		    .add(Network.createRegion("Region 1")
		        .add(Network.createLayer("Layer 2/3", p)
		
		            .add(new TemporalMemory())
		            .add(new SpatialPooler())));
//		            .add(Sensor.create(FileSensor::create, SensorParams.create(
//	                        Keys::path, "", ResourceLocator.path("rec-center-hourly.csv"))))));
		
		network.observe().subscribe(new Action1<Inference>() {

	        @Override
	        public void call(Inference s) {
	        	System.out.println("whaddup " + Arrays.toString(s.getSparseActives()) + "!");
	        	System.out.println(Arrays.toString(s.getEncoding()));
	            System.out.println("Hello " + Arrays.toString(s.getPredictedColumns()) + "!");
	            System.out.println("yoyo " + Arrays.toString(s.getSDR()) + "!");
//	            printNum(s.getSDR(), 28, "*", "-");
//	            network.compute(new int[] {r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2),r.nextInt(2)});
	        }

		});
		try {
			Files.lines(new File("/Users/joshuarose/Desktop/htm.java/src/main/java/org/numenta/nupic/datagen/1.csv").toPath()).forEach(l -> {
				System.out.println("line = " + l);
				int[] array = Arrays.asList(l.split(",")).stream().mapToInt(Integer::parseInt).toArray();
				network.compute(array);
			});
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void hello(String... names) {
	    Observable.from(names).subscribe(new Action1<String>() {

	        @Override
	        public void call(String s) {
	            System.out.println("Hello " + s + "!");
	        }

	    });
	}
	
	// Convenience method carried over form a python script. 
	public static void printNum(int[] numRow, int n, String activeChar, String inactiveChar) {
		String outputString = "";
		int counter = 1;
		for (int i : numRow) {
			outputString += (i > 0 ? activeChar : inactiveChar);
			
			if ( counter % n == 0)
				outputString += "\n";
			
			counter++;
		}
		System.out.println(outputString);		
	}
}
