package methodmodifiercheck;

public class TabManage {
	/**
	 * ただタブを挿入するためのメソッド
	 * @param length
	 * @return
	 */
	public String Tab(int length) {
		StringBuilder sBuilder = new StringBuilder();
		for(int i=0;i<length;++i) {
			sBuilder.append("|\t");
		}
		return sBuilder.toString();
	}
}
