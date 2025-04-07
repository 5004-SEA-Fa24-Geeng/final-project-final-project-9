package Model.output;

import Model.PostedAnimal;

import java.io.OutputStream;
import java.util.List;

public interface IDataNameModel {

    String DATABASE = "data/hostrecords.xml";

    /**
     * Get the records as a list.
     *
     * @return the list of records
     */
    List<PostedAnimal> getRecords();


    /**
     * Gets a single record by hostname.
     *
     * @return the postAnimal
     */
    PostedAnimal getRecord(String hostname);


    /**
     * Writes out the records to the outputstream.
     *
     * OutputStream could be System.out or a FileOutputStream.
     *
     * @param listOfAnimals the list to write, could be a single entry.
     * @param format the format to write the records in
     * @param out the output stream to write to
     */
    static void writeRecords(List<PostedAnimal> listOfAnimals, Formats format, OutputStream out) {
        //IDataFormatter.write(listOfAnimals, format, out);
    }
}
