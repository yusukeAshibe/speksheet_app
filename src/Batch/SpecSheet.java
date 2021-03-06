package batch;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SpecSheet {
	public static void main(String[] args) {

		String firstPath = null;
		if (args.length == 0) {// 引数なしの場合はカレントディレクトリを代入
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

		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> bigList = new ArrayList<String>();
		ArrayList<Long> lastModifiedList = new ArrayList<Long>();
		// 第2階層
		String PathNW = "\\01.NW\\";
		String PathJava = "\\02.java\\";
		String PathML = "\\04.ML\\";

		String[] secondPathList = { PathNW, PathJava, PathML };

		int i = 0;

		for (String secondPath : secondPathList) {

			File targetFolder = new File(firstPath + secondPath);

			// 第3階層
			for (String thirdPath : targetFolder.list()) {
				if (thirdPath.matches("^[0-9]{6}_.*")) {// yyyymm_カナ文字_（例）201108_ラクスタロウ_ラクス太郎

					targetFolder = new File(firstPath + secondPath + thirdPath);
					// 最終階層
					for (String fourthPath : targetFolder.list()) {
						if (fourthPath.matches("(NW|AP|ML)-\\d{3}-\\d{4}.*.(xls|xlsx|pdf)$")) { // （例）NW_201_9999.xls(x)
																								// ||

							String[] lastPathList = fourthPath.split("-", 3);// 最終階層を - で分割
							String lastPath = lastPathList[2];// 分割した前から三番目の文字列を取得
							String personalEngineerId = lastPath.substring(0, 4);// 頭の4桁の数字を取得

							// 最終的なpath
							targetFolder = new File(firstPath + secondPath + thirdPath + "\\" + fourthPath);

							// 最終更新日時の取得
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Long lastModified = targetFolder.lastModified();
							String lastModifiedStr = sdf.format(lastModified);// 見やすいように整形
							String content = personalEngineerId + "," + lastModifiedStr + "," + targetFolder; // 社員番号
							list1.add(personalEngineerId);
							if (list1.size() > 1) {
								list2.add(personalEngineerId);
								if (list1.get(i).equals(list2.get(i))) {

									Long lastlastModified = lastModifiedList.get(lastModifiedList.size() - 1);// listの一つ前の最終更新日を取得
									if (lastlastModified < lastModified) {// 一つ前の最終更新日の方が遅かったら
										bigList.remove(bigList.size() - 1);
										bigList.add(content);
										lastModifiedList.remove(lastModifiedList.size() - 1);
										lastModifiedList.add(lastModified);
									}
								} else {
									bigList.add(personalEngineerId + "," + lastModifiedStr + "," + targetFolder);
									lastModifiedList.add(lastModified);
								}
							} else {
								bigList.add(personalEngineerId + "," + lastModifiedStr + "," + targetFolder);
								lastModifiedList.add(lastModified);
							}
							if (list1.size() > 1) {
								i++;
							}
						}

					}

				}

			}
		}
		for (String content : bigList) {
			System.out.println(content);
		}
	}
}
