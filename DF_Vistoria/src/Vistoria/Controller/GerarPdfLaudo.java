package Vistoria.Controller;

import Vistoria.Model.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDFont;

import javax.swing.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class GerarPdfLaudo {

    public void gerarPdf(Vistoria vistoria, Cliente cliente, Veiculo veiculo, Pagamento pagamento, Funcionario vistoriador) {
        
        // Define o local onde o PDF será salvo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Laudo PDF");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Documentos", "pdf"));
        
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Carregar o logo
                PDImageXObject logo = PDImageXObject.createFromFile("src/imagens/logo.png", document);
                
                // Redimensionar a imagem (50% do tamanho original, por exemplo)
                float scale = 0.5f; // Fator de escala (0.5 = 50% do tamanho original)
                float logoWidth = logo.getWidth() * scale;
                float logoHeight = logo.getHeight() * scale;

                // Posicionamento e fontes
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                
                // Criar instâncias das fontes corretamente
                PDFont fontHelveticaBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                PDFont fontHelvetica = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
                
                // ====================================================================
                // CABEÇALHO - Imagem centralizada no topo
                // ====================================================================
                // Centralizar a imagem
                float logoX = (page.getMediaBox().getWidth() - logoWidth) / 2;
                contentStream.drawImage(logo, logoX, yStart - logoHeight, logoWidth, logoHeight);
                
                // Posição Y após a imagem (com um espaçamento de 20 pixels)
                float yPosition = yStart - logoHeight - 40;

                // Título abaixo da imagem
                contentStream.beginText();
                contentStream.setFont(fontHelveticaBold, 20);
                String title = "RELATÓRIO DE VISTORIA";
                float titleWidth = fontHelveticaBold.getStringWidth(title) / 1000 * 20;
                float titleX = (page.getMediaBox().getWidth() - titleWidth) / 2;
                contentStream.newLineAtOffset(titleX, yPosition);
                contentStream.showText(title);
                contentStream.endText();
                
                // Data de emissão no canto superior direito
                contentStream.beginText();
                contentStream.setFont(fontHelvetica, 10);
                String dataEmissao = "Emitido em: " + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                float dataWidth = fontHelvetica.getStringWidth(dataEmissao) / 1000 * 10;
                float dataX = page.getMediaBox().getWidth() - margin - dataWidth;
                contentStream.newLineAtOffset(dataX, yStart - 15);
                contentStream.showText(dataEmissao);
                contentStream.endText();

                // ====================================================================
                // CONTEÚDO - Iniciar abaixo do título
                // ====================================================================
                yPosition -= 40; // Espaçamento após o título

                // --- Seção Dados do Laudo ---
                yPosition = addSectionTitle(contentStream, "Dados do Laudo", yPosition, margin, fontHelveticaBold);
                yPosition = addDataRow(contentStream, "Data da Vistoria:", vistoria.getDataVistoria().toString(), yPosition, margin, fontHelveticaBold, fontHelvetica);
                yPosition = addDataRow(contentStream, "Resultado:", vistoria.getResultado().toString(), yPosition, margin, fontHelveticaBold, fontHelvetica);
                yPosition = addDataRow(contentStream, "Vistoriador:", vistoriador != null ? vistoriador.getNome() : "Não informado", yPosition, margin, fontHelveticaBold, fontHelvetica);

                // --- Seção Cliente e Veículo ---
                yPosition = addSectionTitle(contentStream, "Cliente e Veículo", yPosition, margin, fontHelveticaBold);
                yPosition = addDataRow(contentStream, "Cliente:", cliente.getNome(), yPosition, margin, fontHelveticaBold, fontHelvetica);
                yPosition = addDataRow(contentStream, "Veículo:", veiculo.getNome_veiculo() + " " + veiculo.getModelo(), yPosition, margin, fontHelveticaBold, fontHelvetica);
                yPosition = addDataRow(contentStream, "Placa:", veiculo.getPlaca(), yPosition, margin, fontHelveticaBold, fontHelvetica);
                yPosition = addDataRow(contentStream, "Chassi:", veiculo.getChassi(), yPosition, margin, fontHelveticaBold, fontHelvetica);

                // --- Seção Pagamento ---
                yPosition = addSectionTitle(contentStream, "Pagamento", yPosition, margin, fontHelveticaBold);
                yPosition = addDataRow(contentStream, "Valor:", pagamento != null ? "R$ " + pagamento.getValor().toString() : "Pendente", yPosition, margin, fontHelveticaBold, fontHelvetica);
                yPosition = addDataRow(contentStream, "Forma:", pagamento != null ? pagamento.getFormaPagamento().toString() : "Pendente", yPosition, margin, fontHelveticaBold, fontHelvetica);

                // --- Seção Observações ---
                yPosition = addSectionTitle(contentStream, "Observações", yPosition, margin, fontHelveticaBold);
                yPosition = addTextArea(contentStream, vistoria.getObservacoes(), yPosition, margin, page.getMediaBox().getWidth() - 2 * margin, fontHelvetica);

                contentStream.close();
                document.save(filePath);

                JOptionPane.showMessageDialog(null, "Laudo salvo com sucesso em: " + filePath, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao gerar o PDF: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private float addSectionTitle(PDPageContentStream contentStream, String title, float yPosition, float margin, PDFont font) throws IOException {
        yPosition -= 20;
        contentStream.beginText();
        contentStream.setFont(font, 14);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(title);
        contentStream.endText();
        contentStream.moveTo(margin, yPosition - 3);
        contentStream.lineTo(550, yPosition - 3);
        contentStream.stroke();
        return yPosition - 15;
    }
    
    private float addDataRow(PDPageContentStream contentStream, String key, String value, float yPosition, float margin, PDFont fontBold, PDFont fontNormal) throws IOException {
        yPosition -= 15;
        contentStream.beginText();
        contentStream.setFont(fontBold, 12);
        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText(key);
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(fontNormal, 12);
        contentStream.newLineAtOffset(margin + 150, yPosition); // Alinhamento da segunda coluna
        contentStream.showText(value);
        contentStream.endText();
        
        return yPosition;
    }
    
    private float addTextArea(PDPageContentStream contentStream, String text, float yPosition, float margin, float width, PDFont font) throws IOException {
        if (text == null || text.isEmpty()) {
            return yPosition;
        }

        contentStream.setFont(font, 12);
        
        // Simulação de quebra de linha
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
             yPosition -= 15;
             if (yPosition < margin) { // Para evitar que o texto saia da página
                 break;
             }
             contentStream.beginText();
             contentStream.newLineAtOffset(margin, yPosition);
             contentStream.showText(line);
             contentStream.endText();
        }
        
        return yPosition;
    }
}