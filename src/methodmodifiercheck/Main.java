package methodmodifiercheck;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Main {

	private static final String PACKAGE_PATH = "src/";
	private static final String JAVA_PACKAGE = "methodmodifiercheck/";
//	private static final String JAVA_SOURCE = "Main.java";


	private static void MyVisitor(String filePath) {

		List<String> lines = null;
		// ファイル内部の文字列を読み込み
		try {
			lines = Files.readAllLines(Paths.get(filePath),StandardCharsets.ISO_8859_1);
		}
		// 読み込みの際に何らかのエラーが生じたときは何もせずにプログラムを終了
		catch (Exception e) {
			System.err.println(e.getMessage());
			return;
		}

		// ASTの構築
		// (参照 : https://sdl.ist.osaka-u.ac.jp/~s-kimura/jdtdoc/jdt.html)

		ASTParser parser = ASTParser.newParser(AST.JLS10);
		parser.setSource(String.join(System.lineSeparator(), lines).toCharArray());
		CompilationUnit unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());

		MethodVisitor methodVisitor = new MethodVisitor();
		unit.accept(methodVisitor);
	}

	public static void main(final String[] args) {
		String filePath = PACKAGE_PATH + JAVA_PACKAGE;

	    File dir = new File(filePath);
	    File[] list = dir.listFiles();
	    for(int i=0; i<list.length; i++) {
	      if(list[i].getName().contains(".java")) {
	        System.out.println(list[i].getName());
	        MyVisitor(filePath + list[i].getName());
	      }
	    }
	}
}
