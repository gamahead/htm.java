package gamahead.sandbox;

import java.util.Arrays;

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
		
		Parameters p = Parameters.getAllDefaultParameters();
		
		Network network = Network.create("Network API Demo", p)
		    .add(Network.createRegion("Region 1")
		        .add(Network.createLayer("Layer 2/3", p)
		            .add(new TemporalMemory())
		            .add(new SpatialPooler())));
//		            .add(Sensor.create(FileSensor::create, SensorParams.create(
//	                        Keys::path, "", ResourceLocator.path("rec-center-hourly.csv"))))));
		network.start();

		System.out.println("RIGHT 1");
        System.out.println("RIGHT 2");
        System.out.println("RIGHT 3");
        System.out.println("RIGHT 4");
	}
	
	public static void hello(String... names) {
	    Observable.from(names).subscribe(new Action1<String>() {

	        @Override
	        public void call(String s) {
	            System.out.println("Hello " + s + "!");
	        }

	    });
	}
}
