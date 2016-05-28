/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import DAO.DAOSchedule;
import DAO.DAOState;
import java.text.ParseException;
import java.util.Date;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Avelino
 */
public class Schedule implements Job {

    private Date d;
    private Date t;
    private State s;
    private Logger log = Logger.getLogger(Schedule.class);

    public Schedule() {
    }

    public Schedule(State s) {
        this.s = s;
    }

    public Schedule(Date d, Date t, State s) {
        this.d = d;
        this.t = t;
        this.s = s;
        //makeSchedule();
    }

    public Schedule(Date d, Date t) {
        this.d = d;
        this.t = t;
        //makeSchedule();
    }

    public Schedule(String d, String t, State s) throws ParseException {
        setD(WUtil.formataData(d));
        setT(WUtil.formataHora(t));
        this.s = s;
        //makeSchedule();
    }

    public Schedule(String d, String t) throws ParseException {
        setD(WUtil.formataData(d));
        setT(WUtil.formataHora(t));
        //makeSchedule();
    }

    public void setSchedule(Date d, Date t) {
        setD(d);
        setT(t);
        makeSchedule();
    }

    public void setSchedule(String d, String t) throws ParseException {
        setD(WUtil.formataData(d));
        setT(WUtil.formataHora(t));
        makeSchedule();
    }

    @Override
    public void execute(JobExecutionContext context) {
        System.err.println("Executando Quartz em: "
                + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
                format(new Date(
                                (System.currentTimeMillis()))));
        /*Logger.getLogger(Schedule.class.getName()).log(Level.SEVERE, "ERRO", context);
         System.err.println("Servico executado conforme agendamento");
         System.err.println("Executando Quartz em: "
         + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
         format(new Date(
         (System.currentTimeMillis()))));*/

        DAOState daoState = new DAOState();
        DAOSchedule daoSchedule = new DAOSchedule();
        String g = context.getTrigger().getKey().getGroup();
        //System.err.println("É este? " + g);
        String json = daoSchedule.getScheduleByGroup(g);
        //System.err.println("JSON=" + json);
        State state = new Gson().fromJson(json, State.class);
        daoState.updateState(state);
    }

    public void makeSchedule() {
        try {
            String grupo = hashPassword.getRandomString(10);

            DAOSchedule daos = new DAOSchedule();
            daos.insertSchedule(new Gson().toJson(s), grupo);

            setD(WUtil.formataData(s.getD()));
            setT(WUtil.formataHora(s.getT()));

            SchedulerFactory schedFact = new StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            //Iniciando o  scheduler
            sched.start();
            //Criando um Job
            JobDetail job = JobBuilder.newJob(Schedule.class)
                    .withIdentity("agendamento", grupo)//Cria uma identidade e associa a um grupo
                    .build();
            //Criando um CronTrigger
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("myTrigger", grupo)//Cria uma identidade e associa a um grupo
                    .withSchedule(CronScheduleBuilder.cronSchedule(
                                    this.t.getSeconds() + " "
                                    + this.t.getMinutes() + " "
                                    + this.t.getHours() + " "
                                    + this.d.getDate() + " "
                                    + (this.d.getMonth() + 1) + " ? "
                                    + (this.d.getYear() + 1900)))//Expressão CronTrigger
                    .build();
            //Adicionamos o job e cron
            sched.scheduleJob(job, trigger);
            /*System.out.println("Criado!!! s="
                    + sched.getSchedulerName()
                    + " trigger=" + trigger.getJobKey().getGroup());*/

        } catch (Exception e) {
            System.out.println("Erro ao tentar agendar uma tarefa:\n\n"
                    + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setSchedule(String segundo,
            String minuto,
            String hora,
            String diaMes,
            String mes,
            String ano) {

        //System.out.println(grupo + grupo + grupo);
        String grupo = hashPassword.getRandomString(10);
        try {
            SchedulerFactory schedFact = new StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            //Iniciando o  scheduler
            sched.start();
            //Criando um Job
            JobDetail job = JobBuilder.newJob(Schedule.class)
                    .withIdentity("agendamento", grupo)//Cria uma identidade e associa a um grupo
                    .build();
            //Criando um CronTrigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", grupo)//Cria uma identidade e associa a um grupo
                    .withSchedule(CronScheduleBuilder.cronSchedule(
                                    segundo + " "
                                    + minuto + " "
                                    + hora + " "
                                    + diaMes + " "
                                    + mes + " ? "
                                    + ano))//Expressão CronTrigger
                    .build();
            //Adicionamos o job e cron
            sched.scheduleJob(job, trigger);

        } catch (Exception e) {
            System.out.println("Erro ao tentar agendar uma tarefa:\n\n"
                    + e.getMessage());
            e.printStackTrace();
        }
    }

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public Date getT() {
        return t;
    }

    public void setT(Date t) {
        this.t = t;
    }

    public State getS() {
        return s;
    }

    public void setS(State s) {
        this.s = s;
    }
}

/* 
 *  *  *  *  *  *      expressão CronTrigger
 -  -  -  -  -  -
 |  |  |  |  |  |
 |  |  |  |  |  +-------------- ano ou dia da semana (aaaa) ou (0 - 6) (Sunday=0)
 |  |  |  |  +----------------- mes                  (1 - 12)
 |  |  |  +-------------------- dia do mes           (1 - 31)
 |  |  +----------------------- horas                (0 - 23)
 |  +-------------------------- minutos              (0 - 59) 
 +----------------------------- segundos             (0 - 59)
 Agendar("0", "50", "10", "22", "10","2016","grupo1"); //Exemplo de uso

 http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
 */
