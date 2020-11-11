package batch;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SpecSheet {
	public static void main(String[] args) {

		String firstPath = null;
		if (args.length == 0) {// �����Ȃ��̏ꍇ�̓J�����g�f�B���N�g������
			firstPath = new File(".").getAbsoluteFile().getParent();
			searchPersonalEngineerIdAndSpecSheetUpdateDate(firstPath);
		} else if (args.length == 1) {
			firstPath = args[0];
			searchPersonalEngineerIdAndSpecSheetUpdateDate(firstPath);
		} else {
			System.out.println("�����͂Ȃ��i�J�����g�f�B���N�g���ɂȂ�j��������1��" + "�i01.NW or 02.java or 04.ML �܂ł�Path �j���w�肵�Ă�������");
		}

	}

	static void searchPersonalEngineerIdAndSpecSheetUpdateDate(String firstPath) {

		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> bigList = new ArrayList<String>();
		ArrayList<Long> lastModifiedList = new ArrayList<Long>();
		// ��2�K�w
		String PathNW = "\\01.NW\\";
		String PathJava = "\\02.java\\";
		String PathML = "\\04.ML\\";

		String[] secondPathList = { PathNW, PathJava, PathML };

		int i = 0;

		for (String secondPath : secondPathList) {

			File targetFolder = new File(firstPath + secondPath);

			// ��3�K�w
			for (String thirdPath : targetFolder.list()) {
				if (thirdPath.matches("^[0-9]{6}_.*")) {// yyyymm_�J�i����_�i��j201108_���N�X�^���E_���N�X���Y

					targetFolder = new File(firstPath + secondPath + thirdPath);
					// �ŏI�K�w
					for (String fourthPath : targetFolder.list()) {
						if (fourthPath.matches("(NW|AP|ML)-\\d{3}-\\d{4}.*.(xls|xlsx|pdf)$")) { // �i��jNW_201_9999.xls(x)
																								// ||

							String[] lastPathList = fourthPath.split("-", 3);// �ŏI�K�w�� - �ŕ���
							String lastPath = lastPathList[2];// ���������O����O�Ԗڂ̕�������擾
							String personalEngineerId = lastPath.substring(0, 4);// ����4���̐������擾

							// �ŏI�I��path
							targetFolder = new File(firstPath + secondPath + thirdPath + "\\" + fourthPath);

							// �ŏI�X�V�����̎擾
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Long lastModified = targetFolder.lastModified();
							String lastModifiedStr = sdf.format(lastModified);// ���₷���悤�ɐ��`
							String content = personalEngineerId + "," + lastModifiedStr + "," + targetFolder; // �Ј��ԍ�
							list1.add(personalEngineerId);
							if (list1.size() > 1) {
								list2.add(personalEngineerId);
								if (list1.get(i).equals(list2.get(i))) {

									Long lastlastModified = lastModifiedList.get(lastModifiedList.size() - 1);// list�̈�O�̍ŏI�X�V�����擾
									if (lastlastModified < lastModified) {// ��O�̍ŏI�X�V���̕����x��������
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
