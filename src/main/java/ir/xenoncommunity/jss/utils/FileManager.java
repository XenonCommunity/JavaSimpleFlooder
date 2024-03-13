package ir.xenoncommunity.jss.utils;

import ir.xenoncommunity.jss.utils.filemanager.Value;
import lombok.experimental.UtilityClass;
import lombok.*;

import java.io.*;
import java.util.*;
@Getter
@Setter
@UtilityClass
public class FileManager {
    public final File folder = new File("JSS");
    public final File config = new File(folder,"config.txt");
    public final List<Value> values = new ArrayList<>();
    public void init(){
        if(folder.exists() && config.exists()){
            readConf();
        } else{
            saveForFirstTime();
        }
    }
    @SneakyThrows
    public void readConf(){
        val br = new BufferedReader(new FileReader(config));
        values.forEach(e -> {
            try {
                e.setValue(br.readLine().split("_")[1]);
            } catch (final Exception ignored) {}
        });
        br.close();
    }
    @SneakyThrows
    public void saveForFirstTime(){
        folder.mkdirs();
        config.createNewFile();
        val bw = new BufferedWriter(new FileWriter(config));
        values.forEach(e -> {
            try {
                bw.write(e.getName() + "_" + e.getValue());
                bw.newLine();
            } catch (Exception ignored) {}
        });
        bw.close();
    }
}
