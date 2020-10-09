package batch;

import java.io.File;
import java.text.SimpleDateFormat;

public class SpecSheet {
	public static void main(String[] args) {

		// 第1階層
		// String firstPath = "C:\\specsheet";

		String firstPath = null;		
		if (args.length == 0) {//引数なしの場合はカレントディレクトリを代入
			firstPath = new File(".").getAbsoluteFile().getParent();
			searchPersonalEngineerIdAndSpecSheetUpdateDate(firstPath);
		} else if (args.length == 1) {
			firstPath = args[0];
			searchPersonalEngineerIdAndSpecSheetUpdateDate(firstPath);
		} else {
			System.out.println("引数はなし（カレントディレクトリになる）もしくは1つ" + "（01.NW or 02.java or 04.ML までのPath ）を指定してください");
		}

	}

	static void searchPersonalEngineerIdAndSpecSheetUpdateDate(String firstPath) {
		// 第2階層
		String PathNW = "\\01.NW\\";
		String PathJava = "\\02.java\\";
		String PathML = "\\04.ML\\";

		String[] secondPathList = { PathNW, PathJava, PathML };

		for (String secondPath : secondPathList) {

			File targetFolder = new File(firstPath + secondPath);

			// 第3階層
			for (String thirdPath : targetFolder.list()) {
				if (thirdPath.matches("^[0-9]{6}_.*")) {// yyyymm_カナ文字_（例）201108_ラクスタロウ_ラクス太郎

					targetFolder = new File(firstPath + secondPath + thirdPath);
					// 最終階層
					for (String fourthPath : targetFolder.list()) {
						if (fourthPath.matches("[A-Z]{2}-\\d{3}-\\d{4}.xls.*$")) { // （例）NW_201_9999.xls(x) ||

							String[] lastPathList = fourthPath.split("-", 3);// 最終階層を - で分割
							String lastPath = lastPathList[2];// 分割した前から三番目の文字列を取得
							String personalEngineerId = lastPath.substring(0, 4);// 頭の4桁の数字を取得

							// 最終的なpath
							targetFolder = new File(firstPath + secondPath + thirdPath + "\\" + fourthPath);

							// 最終更新日時の取得
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Long lastModified = targetFolder.lastModified();
							String lastModifiedStr = sdf.format(lastModified);// 見やすいように整形
							System.out.println(personalEngineerId + "," + lastModifiedStr + "," + targetFolder);// 社員番号
																												// 最終更新日時
																												// Path
																												// ,区切り

						}
					}
				}
			}
		}

	}

}
