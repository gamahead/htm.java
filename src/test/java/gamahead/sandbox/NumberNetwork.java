package gamahead.sandbox;

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
import rx.Subscriber;

public class NumberNetwork {

	public static void main(String[] args) {
		
		Parameters p = NetworkDemoHarness.getParameters();
		p = p.union(NetworkDemoHarness.getNetworkDemoTestEncoderParams());

		Network network = Network.create("Network API Demo", p)
		    .add(Network.createRegion("Region 1")
		        .add(Network.createLayer("Layer 2/3", p)
		            .alterParameter(KEY.AUTO_CLASSIFY, Boolean.TRUE)
		            .add(Anomaly.create())
		            .add(new TemporalMemory())
		            .add(new SpatialPooler())
		            .add(Sensor.create(ObservableSensor::create, SensorParams.create(
		                Keys::path, "", ResourceLocator.path("1.csv"))))));

		network.start();
		
		Observable<Inference> o = network.observe(); // This Observable can be used a million different ways!
		o.subscribe(new Subscriber<Inference>() {
		    @Override 
		    public void onCompleted() {
		        // Do finalization work here if desired
		    }

		    @Override 
		    public void onError(Throwable e) {
		        // Error handling here
		    }

		    @Override public void onNext(Inference i) {
		        System.out.println("\n\nHere's another iteration baby\n\n");
		    }
		});

	}

}
