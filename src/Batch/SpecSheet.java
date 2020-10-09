package Batch;

import java.io.File;
import java.text.SimpleDateFormat;

public class SpecSheet {
	public static void main(String[] args) {

		// 第1階層
		String firstPath = "C:\\specsheet\\";

		// 第2階層
		String PathNW = "01.NW\\";
		String PathJava = "02.java\\";
		String PathML = "04.ML\\";

		String[] secondPathList = { PathNW, PathJava, PathML };

		for (String secondPath : secondPathList) {

			File targetFolder = new File(firstPath + secondPath);

			// 第3階層
			for (String thirdPath : targetFolder.list()) {
				if (thirdPath.matches("^[0-9]{6}_.*")) {// yyyymm_カナ文字_（例）201108_ラクスタロウ_ラクス太郎

					targetFolder = new File(firstPath + secondPath + thirdPath);
					// 最終階層
					for (String fourthPath : targetFolder.list()) {
						if (fourthPath.matches("NW-\\d{3}-\\d{4}.xls.*$")
								|| fourthPath.matches("AP-\\d{3}-\\d{4}.xls.*$")
								|| fourthPath.matches("ML-\\d{3}-\\d{4}.xls.*$")) { // （例）NW_201_9999.xls(x)

							String[] lastPathList = fourthPath.split("-", 3);// 最終階層を - で分割
							String lastPath = lastPathList[lastPathList.length - 1];// 分割した最後を取得
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
