package com.francis.glc;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class JanelaGramatica extends JFrame {
	
	private static final long serialVersionUID = -8790035762434592998L;
	
	private Gramatica gramatica;

	private JTextField textFieldVariavel;	
	private JList<Character> listVariaveis;
	private static JList<Character> listTerminais;
	private JList<String> listProducoes;
	private JLabel lblVariaveis;
	private JTextField txtFieldTerminal;
	private JButton btnAdicionarVariavel;
	private JTextField textFieldProducao;
	private JButton btnAdicionarTerminal;
	private JTextArea labelGramatica;
	private JTextArea lblFng;
	private JTextArea lblFnc;
	private JTextArea labelGramaticaMinimizada;
	private JButton btnAdicionarProducoes;
	private JButton btnRemoverVariavel;
	private JButton btnRemoverTerminal;
	private JButton btnRemoverProducao;
	private JButton btnInstrucoes;
	private JButton btnLimpar;
	private JButton btnGerarExemplo;
	private JLabel lblEmBrancoPara;
	private JLabel label_1;
	private JLabel label;
	private JLabel lblParaCriarUma;
	private JLabel lblProducao;
	private JPanel contentPane;
	private JLabel lblTerminal;
	private JLabel lblVariavel;
	private JLabel lblGramatica;
	private JLabel lblProducoes;
	private JLabel lblTerminais;
	private JLabel lblAdicionarTerminais;
	private JLabel lblAdicionarVariavel;
	private JLabel lblAdicionarProducoes;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JanelaGramatica frame = new JanelaGramatica();
				frame.setVisible(true);
			}
		});
	}

	public JanelaGramatica() {
		setTitle("Gramática Livre de Contexto");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1008, 534);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblAdicionarVariavel = new JLabel("Adicionar Variavel");
		lblAdicionarVariavel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAdicionarVariavel.setBounds(197, 17, 129, 14);
		contentPane.add(lblAdicionarVariavel);
		
		lblAdicionarProducoes = new JLabel("Adicionar Produ\u00E7\u00F5es");
		lblAdicionarProducoes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAdicionarProducoes.setBounds(518, 13, 159, 22);
		contentPane.add(lblAdicionarProducoes);
		
		textFieldVariavel = new JTextField();
		textFieldVariavel.setBounds(253, 42, 86, 20);
		contentPane.add(textFieldVariavel);
		textFieldVariavel.setColumns(10);
		
		lblVariavel = new JLabel("Vari\u00E1vel:");
		lblVariavel.setBounds(197, 45, 46, 14);
		contentPane.add(lblVariavel);
		
		lblVariaveis = new JLabel("Variáveis:");
		lblVariaveis.setBounds(293, 112, 60, 14);
		contentPane.add(lblVariaveis);
		

		
		listVariaveis = new JList<Character>();
		listVariaveis.setValueIsAdjusting(true);
		listVariaveis.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listVariaveis.setForeground(Color.BLACK);
		JScrollPane scrollPanel = new JScrollPane(listVariaveis);
		scrollPanel.setBounds(197, 129, 142, 121);
		contentPane.add(scrollPanel);
		
		btnAdicionarVariavel = new JButton("Adicionar Vari\u00E1vel");
		btnAdicionarVariavel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {					
					if(textFieldVariavel.getText() == null || textFieldVariavel.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(JanelaGramatica.this, "Coloque uma letra maiuscula!");
						return;
					}
					
					Character variavel = textFieldVariavel.getText().trim().charAt(0);
					if (gramatica == null) {
						gramatica = new Gramatica(variavel);
						txtFieldTerminal.setEnabled(true);
						btnAdicionarTerminal.setEnabled(true);
						textFieldProducao.setEnabled(true);
						btnAdicionarProducoes.setEnabled(true);
					} else {
						gramatica.adicionarVariavel(variavel);
					}
					
					// Inserindo na lista(interface) para a inser��o das produ��es.
					listVariaveis.setListData(criarListaVariaveis());
					
					
					// Inserindo na lista(interface) para a inser��o das produ��es.
					listTerminais.setListData(criarListaTerminais());
					atualizarLabels();
			} catch(GramaticaException e) {
				JOptionPane.showMessageDialog(JanelaGramatica.this, e.getMessage());
			}
				
			}
		});
		btnAdicionarVariavel.setBounds(197, 70, 142, 23);
		contentPane.add(btnAdicionarVariavel);
		
		lblGramatica = new JLabel("G = (V, T, P, S)");
		lblGramatica.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGramatica.setBounds(698, 14, 122, 20);
		contentPane.add(lblGramatica);
		
		lblAdicionarTerminais = new JLabel("Adicionar Terminais");
		lblAdicionarTerminais.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAdicionarTerminais.setBounds(12, 13, 159, 22);
		contentPane.add(lblAdicionarTerminais);
		
		lblTerminal = new JLabel("Terminal:");
		lblTerminal.setBounds(11, 45, 46, 14);
		contentPane.add(lblTerminal);
		
		txtFieldTerminal = new JTextField();
		txtFieldTerminal.setEnabled(false);
		txtFieldTerminal.setColumns(10);
		txtFieldTerminal.setBounds(67, 42, 86, 20);
		contentPane.add(txtFieldTerminal);
		
		btnAdicionarTerminal = new JButton("Adicionar Terminal");
		btnAdicionarTerminal.setEnabled(false);
		btnAdicionarTerminal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String terminal = txtFieldTerminal.getText().trim();
					if(terminal == null || terminal.trim().isEmpty()) {
						JOptionPane.showMessageDialog(JanelaGramatica.this, "Insira um caractere maiusculo");
						return;
					}
					gramatica.adicionarTerminais(terminal.charAt(0));
					listTerminais.setListData(criarListaTerminais());
					atualizarLabels();
				} catch(GramaticaException e) {
					JOptionPane.showMessageDialog(JanelaGramatica.this, e.getMessage());
				}
			}
		});
		btnAdicionarTerminal.setBounds(12, 70, 141, 23);
		contentPane.add(btnAdicionarTerminal);
		
		listTerminais = new JList<Character>();
		listTerminais.setValueIsAdjusting(true);
		listTerminais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTerminais.setForeground(Color.BLACK);
		scrollPanel = new JScrollPane(listTerminais);
		scrollPanel.setBounds(12, 129, 141, 121);
		contentPane.add(scrollPanel);
		
		lblTerminais = new JLabel("Terminais:");
		lblTerminais.setBounds(104, 112, 64, 14);
		contentPane.add(lblTerminais);
		
		listProducoes = new JList<String>();
		listProducoes.setValueIsAdjusting(true);
		listProducoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listProducoes.setForeground(Color.BLACK);
		scrollPanel = new JScrollPane(listProducoes);
		scrollPanel.setBounds(518, 129, 141, 121);
		contentPane.add(scrollPanel);
		
		lblProducoes = new JLabel("Produções:");
		lblProducoes.setBounds(604, 111, 72, 14);
		contentPane.add(lblProducoes);
		
		btnAdicionarProducoes = new JButton("Adicionar Produção");
		btnAdicionarProducoes.setEnabled(false);
		btnAdicionarProducoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Character variavel = listVariaveis.getSelectedValue();
					if(variavel == null) {
						JOptionPane.showMessageDialog(JanelaGramatica.this, "Selecione uma variável.");
						return;
					}
					String producao = textFieldProducao.getText().trim();
					gramatica.adicionarProducao(variavel, producao);
					
					listProducoes.setListData(criarListaProducoes());
					atualizarLabels();
				} catch(GramaticaException ex) {
					JOptionPane.showMessageDialog(JanelaGramatica.this, ex.getMessage());
				}
			}
		});
		btnAdicionarProducoes.setBounds(518, 70, 141, 23);
		contentPane.add(btnAdicionarProducoes);		
		
		textFieldProducao = new JTextField();
		textFieldProducao.setEnabled(false);
		textFieldProducao.setColumns(10);
		textFieldProducao.setBounds(573, 42, 86, 20);
		contentPane.add(textFieldProducao);
		
		lblProducao = new JLabel("Produção:");
		lblProducao.setBounds(518, 45, 54, 14);
		contentPane.add(lblProducao);
		
		label = new JLabel(" << Selecione uma variável aqui");
		label.setBounds(349, 137, 159, 14);
		contentPane.add(label);
		
		lblParaCriarUma = new JLabel("Para criar uma produção aqui >>");
		lblParaCriarUma.setBounds(349, 162, 159, 14);
		contentPane.add(lblParaCriarUma);
		
		labelGramatica = new JTextArea("Gramática:");
		scrollPanel = new JScrollPane(labelGramatica);
		scrollPanel.setBounds(670, 84, 320, 198);
		contentPane.add(scrollPanel);
		
		labelGramaticaMinimizada = new JTextArea("Gramática Minimizada:");
		scrollPanel = new JScrollPane(labelGramaticaMinimizada);
		scrollPanel.setBounds(12, 295, 320, 198);
		contentPane.add(scrollPanel);
		
		lblFnc = new JTextArea("FNC:");
		scrollPanel = new JScrollPane(lblFnc);
		scrollPanel.setBounds(340, 295, 320, 198);
		contentPane.add(scrollPanel);
		
		lblFng = new JTextArea("FNG:");
		scrollPanel = new JScrollPane(lblFng);
		scrollPanel.setBounds(670, 295, 320, 198);
		contentPane.add(scrollPanel);
		
		btnGerarExemplo = new JButton("Gerar Exemplo");
		btnGerarExemplo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gerarExemplo();
			}
		});
		btnGerarExemplo.setBounds(695, 50, 142, 23);
		contentPane.add(btnGerarExemplo);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(847, 50, 142, 23);
		contentPane.add(btnLimpar);
		
		lblEmBrancoPara = new JLabel("Em branco para Ø >>");
		lblEmBrancoPara.setBounds(398, 45, 110, 14);
		contentPane.add(lblEmBrancoPara);
		
		label_1 = new JLabel(" << Começe por aqui");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(349, 73, 159, 14);
		contentPane.add(label_1);
		
		btnRemoverTerminal = new JButton("Remover Terminal");
		btnRemoverTerminal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Character terminal = listTerminais.getSelectedValue();
					if(terminal == null) {
						return;
					}
					gramatica.removerTerminal(terminal);
					listTerminais.setListData(criarListaTerminais());
					listProducoes.setListData(criarListaProducoes());
					atualizarLabels();
				} catch(GramaticaException e) {
					JOptionPane.showMessageDialog(JanelaGramatica.this, e.getMessage());
				}
			}
		});
		btnRemoverTerminal.setBounds(12, 259, 141, 23);
		contentPane.add(btnRemoverTerminal);
		
		btnRemoverVariavel = new JButton("Remover Variável");
		btnRemoverVariavel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Character variavel = listVariaveis.getSelectedValue();
					if(variavel == null) {
						return;
					}
					gramatica.removerVariavel(variavel);
					listVariaveis.setListData(criarListaVariaveis());
					listProducoes.setListData(criarListaProducoes());
					atualizarLabels();
				} catch(GramaticaException ex) {
					JOptionPane.showMessageDialog(JanelaGramatica.this, ex.getMessage());
				}
			}
		});
		btnRemoverVariavel.setBounds(197, 259, 142, 23);
		contentPane.add(btnRemoverVariavel);
		
		btnRemoverProducao = new JButton("Remover Produção");
		btnRemoverProducao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String prod = listProducoes.getSelectedValue();
					if(prod == null) {
						return;
					}
					gramatica.removerProducao(prod.charAt(0), prod.substring(prod.indexOf(" -> ") + " -> ".length()));
					listProducoes.setListData(criarListaProducoes());
					atualizarLabels();
				} catch(GramaticaException ex) {
					JOptionPane.showMessageDialog(JanelaGramatica.this, ex.getMessage());
				}
			}
		});
		btnRemoverProducao.setBounds(518, 258, 141, 23);
		contentPane.add(btnRemoverProducao);
		
		btnInstrucoes = new JButton("Instruções");
		btnInstrucoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(JanelaGramatica.this, "Use apenas 1 letra maiúscula para variáveis e 1 letra minúscula para terminais."
						+ "\nComeçe adicionando uma variável ou abrindo o exemplo."
						+ "\nA primeira variável sempre será a inicial."
						+ "\nSelecione uma variável para adicionar uma produção à ela."
						+ "\nUse nas produções apenas variáveis ou terminais previamente adicionados."
						+ "\nA aplicação se limita a 26 variáveis (letras do alfabeto).", "Instruções", 1);
			}
		});
		btnInstrucoes.setBounds(847, 15, 142, 23);
		contentPane.add(btnInstrucoes);
	}
	
	private Character[] criarListaVariaveis() {
		List<Character> l = new ArrayList<Character>();
		for(Variavel v : gramatica.variaveis) {
			l.add(v.getNome());
		}
		return l.toArray(new Character[l.size()]);
	}
	
	private Character[] criarListaTerminais() {
		return gramatica.terminais.toArray(new Character[gramatica.terminais.size()]);
	}
	
	private String[] criarListaProducoes() {
		List<String> prod = new ArrayList<String>();
		for(Variavel v : gramatica.variaveis) {
			for(String s : v.getProducoes()) {
				String aux = s;
				if (s.equals("")) {
					aux = "Ø";
				}
				prod.add(v.getNome() + " -> " + aux);
			}
		}
		return prod.toArray(new String[prod.size()]);
	}
	
	private void gerarExemplo() {
		gramatica = new Gramatica('S');
		gramatica.adicionarVariavel('B');
		gramatica.adicionarVariavel('C');
		gramatica.adicionarVariavel('D');
		gramatica.adicionarTerminais('a');
		gramatica.adicionarTerminais('b');
		gramatica.adicionarTerminais('d');
		gramatica.adicionarProducao('S', "aB");
		gramatica.adicionarProducao('B', "b");
		gramatica.adicionarProducao('B', "C");
		gramatica.adicionarProducao('C', "BDB");
		gramatica.adicionarProducao('D', "d");
		gramatica.adicionarProducao('D', "");
		
		txtFieldTerminal.setEnabled(true);
		btnAdicionarTerminal.setEnabled(true);
		textFieldProducao.setEnabled(true);
		btnAdicionarProducoes.setEnabled(true);
		listTerminais.setListData(criarListaTerminais());
		listVariaveis.setListData(criarListaVariaveis());
		listProducoes.setListData(criarListaProducoes());
		atualizarLabels();		
	}
	
	private void limpar() {
		gramatica = null;
		
		txtFieldTerminal.setEnabled(false);
		btnAdicionarTerminal.setEnabled(false);
		textFieldProducao.setEnabled(false);
		btnAdicionarProducoes.setEnabled(false);
		listTerminais.setListData(new Character[0]);
		listVariaveis.setListData(new Character[0]);
		listProducoes.setListData(new String[0]);
		txtFieldTerminal.setText("");
		textFieldProducao.setText("");
		textFieldVariavel.setText("");
		
		atualizarLabels();		
	}

	private void atualizarLabels() {
		labelGramatica.setText(gramatica == null ? "Gramática:" : "Gramática:\n" + gramatica);
		
		try {
			labelGramaticaMinimizada.setText("Gramática minimizada:\n" + MinimizadorGramatica.minimizar(gramatica));
		} catch (Exception e) {
			labelGramaticaMinimizada.setText("Gramática minimizada:");
		}
		
		try {
			lblFnc.setText("FNC:\n" + FNCGramatica.fnc(gramatica));
		} catch (Exception e) {
			lblFnc.setText("FNC:");
		}
		
		try {
			lblFng.setText("FNG:\n" + FNGGramatica.fng(gramatica));
		} catch (Exception e) {
			lblFng.setText("FNG:");
		}
	}
}
