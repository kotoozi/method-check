package methodmodifiercheck;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodVisitor extends ASTVisitor {

	// メモ : org.eclipse.jdt.core.Modifier を参照して、16進数を二進した時の桁番号をそのまま数字として捉えた
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
	/**
	 * 得られた装飾子情報に対して算術右シフトを行うための数
	 */
	private static final int SRA_SIGNAL = 2;
	/**
	 * コロン
	 */
	public static final String COLON = " : ";

	private StringBuilder _sBuilder;
//	private List<Integer> _methodInfo;
	private Map<Integer, String> _map;

	private int _tabNum;

	/**
	 * コンストラクタ
	 */
	public MethodVisitor(int tabNum) {
		_sBuilder = new StringBuilder();
//		_methodInfo = new ArrayList<>();
		_map = MapCreate();					// 装飾子情報のMap生成
		_tabNum = tabNum;
	}

	public boolean visit(MethodDeclaration node) {
		int getModifier = node.getModifiers();
		String message = new String();
		int modifier = NONE;
		while(getModifier != 0) {
			modifier++;
			if(getModifier % SRA_SIGNAL == 1) {
				message += _map.get(modifier) + " ";
//				_methodInfo.add(modifier);
			}
			getModifier /= SRA_SIGNAL;
		}
		if(modifier == NONE) message += _map.get(NONE);
		_sBuilder.append(new TabManage().Tab(_tabNum) + node.getName().getIdentifier() + COLON + message + System.getProperty("line.separator"));
		return super.visit(node);
	}
	/**
	 * 定数情報と装飾子のテキスト情報をMapにする
	 * @return 定数と装飾子のセットになったMap
	 */
	private Map<Integer, String> MapCreate() {
		Map<Integer, String> map = new HashMap<>();
		map.put(NONE, "NONE");
		map.put(PUBLIC, "PUBLIC");
		map.put(PRIVATE, "PRIVATE");
		map.put(PROTECTED, "PROTECTED");
		map.put(STATIC, "STATIC");
		map.put(FINAL, "FINAL");
		map.put(SYNCHRONIZED, "SYNCHRONIZED");
		map.put(VOLATILE, "VOLATILE");
		map.put(TRANSIENT, "TRANSIENT");
		map.put(NATIVE, "NATIVE");
		map.put(ABSTRACT, "ABSTRACT");
		map.put(STRICTFP, "STRICTFP");
		map.put(DEFAULT, "DEFAULT");
		return map;
	}
	public String GetMessage() {
		return _sBuilder.toString();
	}
//	public List<Integer> GetMethodInfo() {
//		return _methodInfo;
//	}
}
