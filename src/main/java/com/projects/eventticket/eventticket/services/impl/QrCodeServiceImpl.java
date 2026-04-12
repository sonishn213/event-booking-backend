package com.projects.eventticket.eventticket.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.projects.eventticket.eventticket.domain.entity.QrCode;
import com.projects.eventticket.eventticket.domain.entity.Ticket;
import com.projects.eventticket.eventticket.domain.enums.QrCodeStatusEnum;
import com.projects.eventticket.eventticket.exception.QrCodeGenerationException;
import com.projects.eventticket.eventticket.exception.QrCodeNotFoundException;
import com.projects.eventticket.eventticket.repository.QrCodeRepository;
import com.projects.eventticket.eventticket.services.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QRCodeWriter qrCodeWriter;
    private final QrCodeRepository qrCodeRepository;

    @Override
    public QrCode generateQrCode(Ticket ticket) throws QrCodeGenerationException{
        try {
            UUID uniqueId = UUID.randomUUID();

            String qrCodeImage = generateQrCodeImage(uniqueId);

            QrCode qrCode= new QrCode();
            qrCode.setId(uniqueId);
            qrCode.setValue(qrCodeImage);
            qrCode.setTicket(ticket);
            qrCode.setStatus(QrCodeStatusEnum.ACTIVE);

            return qrCodeRepository.saveAndFlush(qrCode);

        } catch (IOException | WriterException e) {
            throw new QrCodeGenerationException("Failed to create qr code",e);
        }
    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(QrCodeNotFoundException::new);

        try{
            return Base64.getDecoder().decode(qrCode.getValue());
        }catch (IllegalArgumentException e){
            log.error("Invalid base64 qr code for ticketId: {}",ticketId,e);
            throw new QrCodeNotFoundException();
        }
    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT
        );

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(qrCodeImage, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
