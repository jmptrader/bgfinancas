/*
Copyright 2015 Jose Robson Mariano Alves

This file is part of bgfinancas.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This package is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.

*/

package badernageral.bgfinancas.modulo.planejamento.item;

import badernageral.bgfinancas.biblioteca.contrato.Categoria;
import badernageral.bgfinancas.biblioteca.contrato.ControladorFormulario;
import badernageral.bgfinancas.modelo.PlanejamentoItem;
import badernageral.bgfinancas.biblioteca.utilitario.Animacao;
import badernageral.bgfinancas.biblioteca.sistema.Botao;
import badernageral.bgfinancas.biblioteca.contrato.Item;
import badernageral.bgfinancas.biblioteca.utilitario.Erro;
import badernageral.bgfinancas.biblioteca.sistema.Kernel;
import badernageral.bgfinancas.biblioteca.utilitario.Validar;
import badernageral.bgfinancas.biblioteca.tipo.Duracao;
import badernageral.bgfinancas.biblioteca.sistema.Janela;
import badernageral.bgfinancas.biblioteca.tipo.Acao;
import badernageral.bgfinancas.biblioteca.tipo.Status;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import badernageral.bgfinancas.template.botao.BotaoFormulario;
import javafx.scene.control.TitledPane;

public final class PlanejamentoItemFormularioControlador implements Initializable, ControladorFormulario {
       
    private Acao acao;
    
    @FXML private TitledPane formulario;
    @FXML private BotaoFormulario botaoController;
    @FXML private Label labelNome;
    @FXML private TextField nome;
    
    private PlanejamentoItem modelo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        formulario.setText(idioma.getMensagem("planejamento")+" - "+idioma.getMensagem("item"));
        Botao.prepararBotaoModal(this, botaoController);
        labelNome.setText(idioma.getMensagem("nome")+":");
    }
    
    @Override
    public void acaoCancelar() {
        Animacao.fadeInOutClose(formulario);
    }
    
    public void cadastrar(String texto){
        acao = Acao.CADASTRAR;
        botaoController.setTextBotaoFinalizar(idioma.getMensagem("cadastrar"));
        nome.setText(texto);
    }
    
    public void alterar(PlanejamentoItem modelo){
        this.modelo = modelo;
        acao = Acao.ALTERAR;
        botaoController.setTextBotaoFinalizar(idioma.getMensagem("alterar"));
        nome.setText(modelo.getNome());
    }
    
    @Override
    public void acaoFinalizar(){
        if(validarFormulario()){
            if(acao == Acao.CADASTRAR){
                if(new PlanejamentoItem().setNome(nome.getText()).consultar()!=null){
                    Janela.showTooltip(Status.ERRO, idioma.getMensagem("restricao_cadastrar"), Duracao.NORMAL);
                    Animacao.fadeInOutClose(formulario);
                }else{
                    PlanejamentoItem p = new PlanejamentoItem(null, nome.getText());
                    p.cadastrar();
                    Kernel.controlador.acaoFiltrar(true);
                    Animacao.fadeInOutClose(formulario);
                }
            }else if(acao == Acao.ALTERAR){
                PlanejamentoItem p = new PlanejamentoItem(modelo.getIdItem(), nome.getText());
                p.alterar();
                Kernel.controlador.acaoFiltrar(true);
                Janela.showTooltip(Status.SUCESSO, idioma.getMensagem("operacao_sucesso"), Duracao.CURTA);
                Animacao.fadeInOutClose(formulario);
            }
        }
    }
    
    private boolean validarFormulario(){
        try {
            Validar.textField(nome);
            return true;
        } catch (Erro ex) {
            return false;
        }
    }

    @Override
    public void acaoCadastrar(int botao) {
        System.out.println(idioma.getMensagem("nao_implementado"));
    }

    @Override
    public void selecionarComboCategoria(int combo, Categoria categoria) {
        System.out.println(idioma.getMensagem("nao_implementado"));
    }

    @Override
    public void selecionarComboItem(int combo, Item item) {
        System.out.println(idioma.getMensagem("nao_implementado"));
    }

}
