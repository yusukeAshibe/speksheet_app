package batch;

import java.io.File;
import java.text.SimpleDateFormat;

public class SpecSheet {
	public static void main(String[] args) {

		// ��1�K�w
		// String firstPath = "C:\\specsheet";

		String firstPath = null;		
		if (args.length == 0) {//�����Ȃ��̏ꍇ�̓J�����g�f�B���N�g������
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
		// ��2�K�w
		String PathNW = "\\01.NW\\";
		String PathJava = "\\02.java\\";
		String PathML = "\\04.ML\\";

		String[] secondPathList = { PathNW, PathJava, PathML };

		for (String secondPath : secondPathList) {

			File targetFolder = new File(firstPath + secondPath);

			// ��3�K�w
			for (String thirdPath : targetFolder.list()) {
				if (thirdPath.matches("^[0-9]{6}_.*")) {// yyyymm_�J�i����_�i��j201108_���N�X�^���E_���N�X���Y

					targetFolder = new File(firstPath + secondPath + thirdPath);
					// �ŏI�K�w
					for (String fourthPath : targetFolder.list()) {
						if (fourthPath.matches("[A-Z]{2}-\\d{3}-\\d{4}.xls.*$")) { // �i��jNW_201_9999.xls(x) ||

							String[] lastPathList = fourthPath.split("-", 3);// �ŏI�K�w�� - �ŕ���
							String lastPath = lastPathList[2];// ���������O����O�Ԗڂ̕�������擾
							String personalEngineerId = lastPath.substring(0, 4);// ����4���̐������擾

							// �ŏI�I��path
							targetFolder = new File(firstPath + secondPath + thirdPath + "\\" + fourthPath);

							// �ŏI�X�V�����̎擾
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Long lastModified = targetFolder.lastModified();
							String lastModifiedStr = sdf.format(lastModified);// ���₷���悤�ɐ��`
							System.out.println(personalEngineerId + "," + lastModifiedStr + "," + targetFolder);// �Ј��ԍ�
																												// �ŏI�X�V����
																												// Path
																												// ,��؂�

						}
					}
				}
			}
		}

	}

}
