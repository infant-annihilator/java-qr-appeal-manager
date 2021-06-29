package core.qr;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс, предназначенный для генерации QR-кода
 */
public class QRCodeGenerator {
    public static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    /**
     * Функция, создающая QR-код
     * @param text Строка, которую нужно закодировать
     * @param width Ширина формируемого иззображения
     * @param height Высота формируемого иззображения
     * @param filePath Путь, в котором сохранится изображение
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = null;
        try {
            matrix = new com.google.zxing.qrcode.QRCodeWriter().encode(text, com.google.zxing.BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);
    }

    /**
     * Главная функция класса
     * @param fileName Имя генерируемого файла
     * @param text Кодируемая строка
     */
    public static void main(String fileName, String text) {
//        String fileName = QR_CODE_IMAGE_PATH;
        try {
            generateQRCodeImage(text, 100, 100, fileName);
            System.out.println("QR сгенерирован!");
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }
}