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

	private static final String PACKAGE_PATH = "C:\\Users\\k-kotou\\git\\kGenProg\\src\\main\\java\\jp\\kusumotolab\\kgenprog\\";
	private static final String OUTPUT_FILE = "sample.txt";
//	private static final String OUTPUT_FILE_CSV = "sample.csv";
	private static OutputText _output;


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
	    File dir = new File(packagePath);
	    File[] list = dir.listFiles();
	    for(File file : list) {
	    	if(file.isDirectory()) {										// ディレクトリだった場合
	    		RunPerProjectFile(packagePath + file.getName() + "\\");		// ディレクトリに対してメソッド検索(再帰)
	    	}
	    	else {															// ディレクトリでなかった場合
	    		if(file.getName().contains(".java")) {						// Javaファイルをメソッド検出対象ファイルとして扱う
			        System.out.println("|-"+file.getName());				//
			        MyVisitor(packagePath + file.getName());
		    	}
	    	}
	    }
	}

	public static void main(final String[] args) {
		String dirPath = PACKAGE_PATH;
		_output = new OutputText(OUTPUT_FILE);
		RunPerProjectFile(dirPath);
	}
}
