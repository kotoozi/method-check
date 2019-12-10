package methodmodifiercheck;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodVisitor extends ASTVisitor {

	// org.eclipse.jdt.core.Modifier を参照して、16進数を二進した時の桁番号をそのまま数字として捉えた
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


  public boolean visit(MethodDeclaration node) {
	  Map<Integer, String> map = new HashMap<>();
	  map = mapCreate();
	  int getModifier = node.getModifiers();
	  if(getModifier == NONE) {
		  System.out.println("メソッドは装飾子が指定されていません");
		  return super.visit(node);
	  }
	  String message = " : ";//"以下の属性を持っています : ";
	  int modifier = NONE;
	  while(getModifier != 0) {
		  modifier++;
		  if(getModifier % 2 == 1) message += map.get(modifier) + " ";
		  getModifier /= 2;
	  }
	  System.out.print("|	|-"+node.getName().getIdentifier());
	  System.out.println(message);
      return super.visit(node);
  }
  private Map<Integer, String> mapCreate() {
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

}
