/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geradoroz;

import java.awt.Desktop;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author alcimar
 */
public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    CalendarioFormat data = new CalendarioFormat();
    int tipoAtendimento = 0;
    String linhas = "\n\r";
    String OsPDF;

    public TelaPrincipal() {
        initComponents();
        //setIcon();
        ListaImpressora();
        data.SO_gerada("MM");
    }

    public String Clientes() {

        return "s";
    }

    public String Atendimento(int tipo) {
        switch (tipo) {
            case 1:
                return "(X)Presencial ( )Nao Presencial";
            case 2:
                return "( )Presencial (X)Nao Presencial";
            default:
                return "( )Presencial ( )Nao Presencial";
        }
    }

    public String Os(String cliente, String endereco, String cidade, String tecnico, String motivo, String diagnostico, int tipoA) {

        // "\n\r 01234567890123456789012345678901234567890123456789"
        String dataHoje = "___/___/______";
        if (jcb_hoje.isSelected()) {
            dataHoje = data.SO_gerada("dd");
        }
        String os
                = "\n\r Gerado em " + data.SO_gerada() + " by AlciTech   "
                + "\n\r                   GeradorOZ"
                + "\n\r                ORDEM DE SERVICO   "
                + "\n\r Atendimento:" + Atendimento(tipoA)
                //+ "\n\r -----------------------------------------------"
                //+ "\n\r _______________________________________________"
                + "\n\r"
                + "\n\r Cliente:" + cliente
                + "\n\r Endereco: " + endereco + " Cidade: " + cidade
                + "\n\r Inicio: " + dataHoje + " Hora: ____:____ "
                + "\n\r Fim: " + dataHoje + " Hora: ____:____ "
                + "\n\r Tecnico:" + tecnico
                + "\n\r Motivo: " + motivo
                + "\n\r                 SOBRE O SERVICO"
                + "\n\r _______________________________________________"
                + "\n\r Parecer sobre o servico: \n\r " + diagnostico
                + "\n\r _______________________________________________"
                + "\n\r             Aceite do atendimento"
                + "\n\r "
                + "\n\r .______________________________________________"
                + "\n\r                   ASSINATURA"
                + "\n\r  "
                + Quebra_de_linhas((int) spLinhas.getValue())
                + "\n\f";
        return os;
    }

    public String Quebra_de_linhas(int x) {
        linhas = "\n\r\n\r\n\r";
        for (int i = 0; i <= x; i++) {
            linhas += "\n\r";
        }
        return linhas;

    }

    public void Feed() {
        Imprimir("\n\r");
    }
    PrintService impressoraSelecionada = null;

    public PrintService[] ListaImpressora() {
        PrintService[] ps = PrintServiceLookup.lookupPrintServices(null, null);
        jcbImpressora.addItem("Padrão");
        for (PrintService psi : ps) {
            jcbImpressora.addItem(psi.getName());
        }
        return ps;
    }

    public PrintService ImpressoraSelecionada(PrintService[] psi) {
        for (int i = 0; i < psi.length; i++) {
            if (psi[i].getName() == jcbImpressora.getSelectedItem()) {
                return psi[i];
            }
        }
        return PrintServiceLookup.lookupDefaultPrintService();
    }

    public void Imprimir(String trabalho) {
        try {
            //buscaImpressora();
            PrintRequestAttributeSet printerAttributes = new HashPrintRequestAttributeSet();
            InputStream print = new ByteArrayInputStream(trabalho.getBytes());
            DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc texto = new SimpleDoc(print, docFlavor, null);
            PrintService impressora = ImpressoraSelecionada(ListaImpressora());

            printerAttributes.add(new JobName("Ordem de servico", null));
            printerAttributes.add(OrientationRequested.PORTRAIT);
            printerAttributes.add(MediaSizeName.ISO_A4);
            DocPrintJob printJob = impressora.createPrintJob();
            PrinterJob pj = PrinterJob.getPrinterJob();

            JOptionPane.showMessageDialog(null, printJob.getPrintService());
            printJob.print(texto, printerAttributes);
            //Desktop.getDesktop().print(new File("C:\\Users\\alcimar\\Desktop\\tarefas"+os+".pdf"));
        } catch (PrintException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage());
        }
    }

    public void CriarArquivo(String nome, String caminho) {
        String os = Os(txtCliente.getText(), txtEndereco.getText(), txtCidade.getText(), jcbTecnico.getSelectedItem() + "", jcbMotivo.getSelectedItem() + "", txtDiagnostico.getText(), tipoAtendimento);
        
        File arquivo = new File(caminho+"\\"+nome);
        
        try {
            arquivo.createNewFile();
            System.out.println("Arquivo criado " + caminho+nome);
        } catch (IOException ex) {
            System.out.println("Erro ao criar arquivo");
            JOptionPane.showMessageDialog(null, "Erro ao criar arquivo");
        }
        try {
            FileWriter fw = new FileWriter(arquivo);
            BufferedWriter bw = new BufferedWriter(fw);
           
            bw.write(os);
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao criar arquivo");
        }

    }

    public void Limpar() {
        txtCidade.setText(null);
        txtCliente.setText(null);
        txtDiagnostico.setText(null);
        txtEndereco.setText(null);
        txtTelefone.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jcbMotivo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiagnostico = new javax.swing.JTextArea();
        txtEndereco = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jcbTecnico = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jbrpresencial = new javax.swing.JRadioButton();
        jbrnaopresencial = new javax.swing.JRadioButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        spLinhas = new javax.swing.JSpinner();
        jButton3 = new javax.swing.JButton();
        jcbImpressora = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jcb_hoje = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(0, 0));
        setName("Ordem de Serviço i7"); // NOI18N
        setPreferredSize(new java.awt.Dimension(500, 600));
        setResizable(false);

        jButton1.setText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setText("GeradorOZ");

        jLabel2.setText("Cliente:");

        jLabel3.setText("Endereço:");

        jLabel4.setText("Telefone:");

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #-####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel5.setText("Motivo:");

        jcbMotivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Treinamento", "Suporte", "Ajuda", "Instalacao" }));

        jLabel6.setText("Parecer sobre o servico:");

        txtDiagnostico.setColumns(20);
        txtDiagnostico.setLineWrap(true);
        txtDiagnostico.setRows(5);
        jScrollPane1.setViewportView(txtDiagnostico);

        jLabel7.setText("Tecnico:");

        jcbTecnico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AlcimarTech", "Tech1", "Tech2", "Tech3" }));

        jLabel8.setText("Cidade:");

        txtCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCidadeActionPerformed(evt);
            }
        });

        jButton2.setText("Limpar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel9.setText("Tipo de atendimento:");

        jbrpresencial.setText("Presencial");
        jbrpresencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbrpresencialActionPerformed(evt);
            }
        });

        jbrnaopresencial.setText("Não presencial");
        jbrnaopresencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbrnaopresencialActionPerformed(evt);
            }
        });

        jLabel10.setText("Desenvolvido by AlciTech");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel12.setText("Linhas:");

        jButton3.setText("Feed");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel13.setText("Impressora:");

        jButton4.setText("Salvar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jcb_hoje.setText("Hoje");
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spLinhas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbrpresencial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbrnaopresencial))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcbMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel7))
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcb_hoje))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbImpressora, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jcbMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jcbTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcb_hoje))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbrpresencial)
                    .addComponent(jbrnaopresencial)
                    .addComponent(jLabel9))
                .addGap(27, 27, 27)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jcbImpressora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spLinhas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(jLabel12)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String texto = Os(txtCliente.getText(), txtEndereco.getText(), txtCidade.getText(), jcbTecnico.getSelectedItem() + "", jcbMotivo.getSelectedItem() + "", txtDiagnostico.getText(), tipoAtendimento);
        Imprimir(texto);
        // CriarArquivo(texto, txtcaminho.getText());
        System.out.println(Os(txtCliente.getText(), txtEndereco.getText(), txtCidade.getText(), jcbTecnico.getSelectedItem() + "", jcbMotivo.getSelectedItem() + "", txtDiagnostico.getText(), tipoAtendimento));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Limpar();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCidadeActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        try {
            // TODO add your handling code here:
            Desktop.getDesktop().browse(new URI("https://github.com/alcimarbmx"));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jbrpresencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbrpresencialActionPerformed
        // TODO add your handling code here:
        if (jbrpresencial.isSelected()) {
            jbrnaopresencial.setSelected(false);
            tipoAtendimento = 1;
        }
    }//GEN-LAST:event_jbrpresencialActionPerformed

    private void jbrnaopresencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbrnaopresencialActionPerformed
        // TODO add your handling code here:
        if (jbrnaopresencial.isSelected()) {
            jbrpresencial.setSelected(false);
            tipoAtendimento = 2;
        }
    }//GEN-LAST:event_jbrnaopresencialActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Feed();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
//        CriarArquivo(OsPDF, "D:\\Livros");
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Document", "txt");
        file.setFileFilter(filter);
        file.setDialogTitle("Salvar");
       
        //File fill = file.getCurrentDirectory();
        file.setSelectedFile(new File("arquivo".concat(data.SO_gerada("ddMMyyHHmmss")).concat(".txt")));
        int retorno = file.showSaveDialog(this);
        file.getName();
        
        String re = file.getDialogTitle();
        System.out.println(re);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            System.out.println("ok");
            System.out.println(file.getCurrentDirectory().getAbsolutePath());
            CriarArquivo(file.getSelectedFile().getName()+".txt", file.getCurrentDirectory().getAbsolutePath()+"");
            System.out.println(file.getCurrentDirectory().getAbsolutePath()+"");
            re = file.getSelectedFile()+"";
            System.out.println(re);
        } 
        
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton jbrnaopresencial;
    private javax.swing.JRadioButton jbrpresencial;
    private javax.swing.JComboBox<String> jcbImpressora;
    private javax.swing.JComboBox<String> jcbMotivo;
    private javax.swing.JComboBox<String> jcbTecnico;
    private javax.swing.JCheckBox jcb_hoje;
    private javax.swing.JSpinner spLinhas;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextArea txtDiagnostico;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
//    private void setIcon() {
//        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Capturar.jpg").getFile()));
//        //setIconImage(new ImageIcon(getClass().getResource("Capturar.jpg")));
//        //new javax.swing.ImageIcon(getClass().getResource());
//        
//    }
}
