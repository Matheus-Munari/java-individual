package model;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import repository.Conexao;

import java.util.List;

public class Computador {
    private Integer id_Computador;
    private String departamento;
    private String login;
    private String senha;
    private String chave_acesso;
    private Integer fk_empresa;

    public Computador() {

    }

    public Computador(Integer id_Computador, String departamento, String login, String senha, String chave_acesso, Integer fk_empresa) {
        this.id_Computador = id_Computador;
        this.departamento = departamento;
        this.login = login;
        this.senha = senha;
        this.chave_acesso = chave_acesso;
        this.fk_empresa = fk_empresa;
    }

    public List<Computador> autenticadorComputador(String login, String senha){
        Conexao conexao = new Conexao();
        JdbcTemplate con = conexao.getConexaoDoBanco();

        List<Computador> computadores = con.query(
                "SELECT * FROM computador WHERE login = '%s' AND senha = '%s'".formatted(login, senha),
                new BeanPropertyRowMapper<>(Computador.class)
        );

        System.out.println("Inserindo...");

        return computadores;
    }

    public void notificacao(String corpoMensagem, String corpoTitulo){
        System.out.println("Estou na notificacao");

        //Creates PowerShell session (we can execute several commands in the same session)
        try (PowerShell powerShell = PowerShell.openSession()) {
            //Execute a command in PowerShell session
            PowerShellResponse response = powerShell.executeCommand("Add-Type -AssemblyName System.Windows.Forms");

            //Execute another command in the same PowerShell session
            response = powerShell.executeCommand("[System.Windows.Forms.MessageBox]::Show(\"%s\", \"%s\")".formatted(corpoMensagem, corpoTitulo));

            //Print results
            System.out.println(response.getCommandOutput());
        } catch(PowerShellNotAvailableException ex) {
            //Handle error when PowerShell is not available in the system
            //Maybe try in another way?
        }
    }

    public Integer getId_Computador() {
        return id_Computador;
    }

    public void setId_Computador(Integer id_Computador) {
        this.id_Computador = id_Computador;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getChave_acesso() {
        return chave_acesso;
    }

    public void setChave_acesso(String chave_acesso) {
        this.chave_acesso = chave_acesso;
    }

    public Integer getFk_empresa() {
        return fk_empresa;
    }

    public void setFk_empresa(Integer fk_empresa) {
        this.fk_empresa = fk_empresa;
    }

    @Override
    public String toString() {
        return "Computador{" +
                "id_Computador=" + id_Computador +
                ", departamento='" + departamento + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", chave_acesso='" + chave_acesso + '\'' +
                ", fk_empresa=" + fk_empresa +
                '}';
    }
}
