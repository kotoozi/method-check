package methodmodifiercheck;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {

	private List<Integer>[] mList;
	public static final int NONE = 0;
	public static final int PUBLIC = 1;
	public static final int PRIVATE = 2;
	public static final int PROTECTED = 3;
	public static final int STATIC = 4;
	public static final int FINAL = 5;
	public static final int SYNCHRONIZED = 6;
	public static final int VOLATILE = 7;
	public static final int TRANSIENT = 8;
	public static final int NATIVE = 9;
	public static final int ABSTRACT = 11;
	public static final int STRICTFP = 12;
	public static final int DEFAULT = 17;

	public Analyzer() {

	}
	public Analyzer(List<List<Integer>> intList) {
		mList = new ArrayList[12];
		Analyze(intList);
	}
	public void Analyze(List<List<Integer>> intList) {
		for(int i = 0; i < intList.size();++i) {
			for(int modifier : intList.get(i)) {
				modifier = ConvModList(modifier);
				if(mList[modifier] == null) mList[modifier] = new ArrayList<Integer>();
				mList[modifier].add(i);
			}
		}
		for(List<Integer> list : mList) {
			if(list == null) continue;
			float avg = Avg(list);
			double dispersion = Dispersion(list, avg);
			System.out.println(avg + ", " + dispersion);
		}
	}
	private float Avg(List<Integer> list) {
		float count = 0f;
		for(int element : list) {
			count = count + element;
		}
		return count / list.size();
	}
	private double Dispersion(List<Integer> list, float avg) {
		double count = 0;
		for(int element : list) {
			count = count + Math.pow(element, 2);
		}
		return count / list.size() - Math.pow(avg, 2);
	}
	public String getResult() {
		return "";
	}
	private int ConvModList(int modifierToken) {
		switch(modifierToken) {
		case NONE:
			return 0;
		case PUBLIC:
			return 1;
		case PRIVATE:
			return 0;
		case PROTECTED:
			return 2;
		case STATIC:
			return 3;
		case FINAL:
			return 4;
		case SYNCHRONIZED:
			return 5;
		case VOLATILE:
			return 6;
		case TRANSIENT:
			return 7;
		case NATIVE:
			return 8;
		case ABSTRACT:
			return 9;
		case STRICTFP:
			return 10;
		case DEFAULT:
			return 11;
		default:
			return 12;
		}
	}
}
