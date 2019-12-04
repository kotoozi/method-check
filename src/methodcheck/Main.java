package methodcheck;

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
	private static final String JAVA_PACKAGE = "methodcheck/";
	private static final String JAVA_SOURCE = "Parser.java";

	public static void main(final String[] args) {

		String filepath = PACKAGE_PATH + JAVA_PACKAGE + JAVA_SOURCE;

		List<String> lines = null;
		// ファイル内部の文字列を読み込み
		try {
			lines = Files.readAllLines(Paths.get(filepath),StandardCharsets.ISO_8859_1);
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
}
