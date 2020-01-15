package methodmodifiercheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	public static final int DEFAULT = 15;
	/**
	 * 得られた装飾子情報に対して算術右シフトを行うための数
	 */
	private static final int SRA_SIGNAL = 2;
	public static final String COLON = " : ";

	private StringBuilder _sb;
	private List<Integer> _methodInfo;

	/**
	 * コンストラクタ
	 */
	public MethodVisitor() {
		_sb = new StringBuilder();
		_methodInfo = new ArrayList<>();
	}

	public boolean visit(MethodDeclaration node) {
		Map<Integer, String> map = MapCreate();			// 装飾子情報のMap生成
		int getModifier = node.getModifiers();
		String message = new String();
		int modifier = NONE;
		while(getModifier != 0) {
			modifier++;
			if(getModifier % SRA_SIGNAL == 1) {
				message += map.get(modifier) + " ";
				_methodInfo.add(modifier);
			}
			getModifier /= SRA_SIGNAL;
		}
		if(modifier == NONE) message += map.get(NONE);
		_sb.append("|	|-"+node.getName().getIdentifier());
		_sb.append(COLON + message);
		_sb.append(System.getProperty("line.separator"));
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
		return _sb.toString();
	}
	public List<Integer> GetMethodInfo() {
		return _methodInfo;
	}
}
