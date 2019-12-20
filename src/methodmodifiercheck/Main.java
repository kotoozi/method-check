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

	private static final String PACKAGE_PATH = "../enshud/src/main/java/enshud/s4/";
	private static final String JAVA_PACKAGE = "compiler/";
	private static Output _output = new Output();
//	private static final String JAVA_SOURCE = "Main.java";


	/**
	 * ASTを生成するためのメソッド
	 * @param filePath 解析を行うファイルのパス
	 */
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
		_output.WriteToFile(methodVisitor.GetMethodInfo());
		System.out.println(methodVisitor.GetMessage());
	}
	// プロジェクト内のJavaファイルごとに実行
	/**
	 * プロジェクトディレクトリ単位でのソースコードの分析を行う
	 * @param packagePath 解析対象であるパッケージのパス
	 */
	private static void RunPerProjectFile(String packagePath) {
		_output.Reset();
	    File dir = new File(packagePath);
	    File[] list = dir.listFiles();
	    for(int i=0; i<list.length; i++) {
	      if(list[i].getName().contains(".java")) {
	        System.out.println("|-"+list[i].getName());
	        MyVisitor(packagePath + list[i].getName());
	      }
	    }
	}

//	private static void OutputToFile(String outputFilePath) {
//		try{
//			File file = new File(outputFilePath);
//			FileWriter fWriter = new FileWriter(file);
//			BufferedWriter bWriter = new BufferedWriter(fWriter);
//
//			bWriter.close();
//		}catch(Exception e) {
//
//		}
//
//	}

	public static void main(final String[] args) {
		String filePath = PACKAGE_PATH + JAVA_PACKAGE;
		RunPerProjectFile(filePath);
	}
}
