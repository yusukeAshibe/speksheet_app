import java.io.File;
import java.text.SimpleDateFormat;

public class specSheet {
	public static void main(String[] args) {

		// ��1�K�w
		String firstPath = "C:\\specsheet\\";

		// ��2�K�w
		String PathNW = "01.NW\\";
		String PathJava = "02.java\\";
		String PathML = "04.ML\\";

		String[] secondPathList = { PathNW, PathJava, PathML };

		for (String secondPath : secondPathList) {

			File targetFolder = new File(firstPath + secondPath);

			// ��3�K�w
			for (String thirdPath : targetFolder.list()) {
				if (thirdPath.matches("^[0-9]{6}_[�@-���[]+_.*")) {// yyyymm_�J�i����_�i��j201108_���N�X�^���E_���N�X���Y

					targetFolder = new File(firstPath + secondPath + thirdPath);
					// �ŏI�K�w
					for (String fourthPath : targetFolder.list()) {
						if (fourthPath.matches("NW-\\d{3}-\\d{4}.xls.*$")
								|| fourthPath.matches("AP-\\d{3}-\\d{4}.xls.*$")
								|| fourthPath.matches("ML-\\d{3}-\\d{4}.xls.*$")) { // �i��jNW_201_9999.xls(x)

							String[] lastPathList = fourthPath.split("-", 3);// �ŏI�K�w�� - �ŕ���
							String lastPath = lastPathList[lastPathList.length - 1];// ���������Ō���擾
							String personalEngineerId = lastPath.substring(0, 4);// ����4���̐������擾

							// �ŏI�I��path
							targetFolder = new File(firstPath + secondPath + thirdPath + "\\" + fourthPath);
							System.out.println("path: " + targetFolder);
							System.out.println("�Ј��ԍ�: " + personalEngineerId);

							// �ŏI�X�V�����̎擾
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Long lastModified = targetFolder.lastModified();
							String lastModifiedStr = sdf.format(lastModified);// ���₷���悤�ɐ��`
							System.out.println("�ŏI�X�V����: " + lastModifiedStr);
							System.out.println("");

						}
					}
				}
			}
		}

	}
}