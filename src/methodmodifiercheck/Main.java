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
	/**
	 * メインメソッド
	 * @param args
	 */
	public static void main(final String[] args) {
		String dirPath = PACKAGE_PATH;
		_output = new OutputText(OUTPUT_FILE);
		RunPerProjectFile(dirPath);
	}
	/**
	 * 検査するパッケージのパス
	 */
	private static final String PACKAGE_PATH = "C:\\Users\\k-kotou\\git\\kGenProg\\src\\main\\java\\jp\\kusumotolab\\kgenprog\\";
	/**
	 * 出力ファイル名
	 */
	private static final String OUTPUT_FILE = "sample.txt";
//	private static final String OUTPUT_FILE_CSV = "sample.csv";
	/**
	 * テキストファイル出力用クラス
	 */
	private static OutputText _output;


	/**
	 * ASTを生成するためのメソッド
	 * @param filePath 解析を行うファイルのパス
	 */
	private static void MyVisitor(String filePath) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(filePath),StandardCharsets.ISO_8859_1);	// ファイル内部の文字列を読み込み
		}
		catch (Exception e) {
			System.err.println(e.getMessage());		// 読み込みの際に何らかのエラーが生じたときは何もせずにプログラムを終了
			return;
		}
		ExecVisitor(lines);
	}

	// ASTの構築
	// (参照 : https://sdl.ist.osaka-u.ac.jp/~s-kimura/jdtdoc/jdt.html)

	/**
	 * ASTVisitorを実際に実行する
	 * @param lines
	 */
	private static void ExecVisitor(List<String> lines) {
		ASTParser parser = ASTParser.newParser(AST.JLS10);											// Parser生成
		parser.setSource(String.join(System.lineSeparator(), lines).toCharArray());					// 検証対象ファイルのテキスト情報を設置
		CompilationUnit unit = (CompilationUnit) parser.createAST(new NullProgressMonitor());		// AST(構文木)を生成
		MethodVisitor methodVisitor = new MethodVisitor();										// メソッド検出クラス
		unit.accept(methodVisitor);																	// 実行
		_output.WriteToFile(methodVisitor.GetMessage());											// 実行結果をファイルに出力
	}
	// プロジェクト内のJavaファイルごとに実行
	/**
	 * プロジェクトディレクトリ単位でのソースコードの分析を行う
	 * @param dirPath 解析対象であるパッケージのパス
	 */
	private static void RunPerProjectFile(String dirPath) {
	    File[] list = new File(dirPath).listFiles();
	    for(File file : list) {
	    	if(file.isDirectory()) {										// ディレクトリだった場合
	    		RunPerProjectFile(dirPath + file.getName() + "\\");			// ディレクトリに対してメソッド検索(再帰)
	    		continue;
	    	}
    		if(file.getName().contains(".java")) {							// Javaファイルをメソッド検出対象ファイルとして扱う
    			_output.WriteToFileLn("|-"+file.getName());
		        MyVisitor(dirPath + file.getName());
	    	}
	    }
	}
}
