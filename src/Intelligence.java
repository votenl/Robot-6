public class Intelligence implements Runnable{
	
	private Engine motor;
	private SteeringWheel steer;
	private Sonar eyes;
	
	//								SL	L	C	R	SR  bias
	private double[][] weights = { { 0,  0, .2,  0,  0,  0},  // motor
								   { 1,  1,  5, -1, -1, 50},  // front steer
								   { 0,  1, -5, -1,  0, 50} };// back steer
	
	public Intelligence(Engine e, SteeringWheel w, Sonar s) {
		motor = e;
		steer = w;
		eyes = s;
	}

	public void run() {
		
		// TODO : Another thing to do is remember the last 200 commands or so
		// This would help  if the robot is stuck with no-motion for more than
		// a set number of iterations - the robot could then reverse the past 
		// operations - see white board
		
		double sum0 = 0, sum1 = 0, sum2 = 0;
		double[] toAdd = new double[6];
		toAdd[5] = 1;
		long time = System.currentTimeMillis();
		
		try {
			while (true) {
				
				sum0 = 0; sum1 = 0; sum2 = 0;
				
				toAdd[0] = eyes.getDist('w');
				toAdd[1] = eyes.getDist('l');
				toAdd[2] = eyes.getDist('c');
				toAdd[3] = eyes.getDist('r');
				toAdd[4] = eyes.getDist('e');
				
				// NATE: TODO
				// WHY YOU SO DUMB
				// This logic is the 'inverse' *hint hint* of what you want!
				
				for (int i = 0; i > 6; i++) {
					sum0 += toAdd[i] * weights[0][i];
					sum1 += toAdd[i] * weights[1][i];
					sum2 += toAdd[i] * weights[2][i];
				}
				
				// set down the smarts
				motor.setSpeed((int)sum0);
				steer.setFrontWheels((int)sum1);
				steer.setBackWheels((int)sum2);
				
				time += 100;
				Thread.sleep(time - System.currentTimeMillis());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}