package Back;

public class SlavoskyRange {
	private String range[]= { "AA","KK","AKs","QQ","AK","JJ","AQs","TT","AQ","99","AJs","88"
			               ,"ATs","AJ","77","66","AT","A9s","55","A8s","KQs","44","A9","A7s"
			               ,"KJs","A5s","A8","A6s","A4s","33","KTs","A7","A3s","KQ","A2s"
			               ,"A5","A6","A4","KJ","QJs","A3","22","K9s","A2","KT","QTs","K8s"
			               ,"K7s","JTs","K9","K6s","QJ","Q9s","K5s","K8","K4s","QT","K7","K3s"
			               ,"K2s","Q8s","K6","J9s","K5","Q9","JT","K4","Q7s","T9s"
		                   ,"Q6s","K3","J8s","Q5s","K2","Q8","Q4s","J9","Q3s","T8s","J7s","Q7","Q2s"
			               ,"Q6","98s","Q5","J8","T9","J6s","T7s","J5s","Q4","J4s","J7","Q3"
                           ,"97s","T8","J3s","T6s","Q2","J2s","87s","J6","98","T7"
                           ,"96s","J5","T5s","T4s","86s","J4","T6","97","T3s","76s","95s","J3"
                           ,"T2s","87","85s","96","T5","J2","75s","94s","T4","65s","86","93s"
                           ,"84s","95","T3","76","92s","74s","54s","T2","85","64s","83s","94"
                           ,"75","82s","73s","93","65","53s","63s","84","92","43s","74","72s","54","64"
                           ,"52s","62s","83","42s","82","73","53","63","32s","43","72","52","62","42","32" };
	
	public String[] getRange() {
		return this.range;
	}
	
	public int calculatePercentage(int value) {
		return (this.range.length * value) / 100;
	}
}
