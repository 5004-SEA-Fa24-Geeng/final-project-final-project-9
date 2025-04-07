package Model.output;

import Model.PostedAnimal;

import java.io.OutputStream;
import java.util.List;

public interface IDataFormatter {

    /**
     * Write the data as XML.
     *
     * @param listOfAnimals the records to write
     * @param out the output stream to write to
     */
    void writeXmlData(List<PostedAnimal> listOfAnimals, OutputStream out);

    /**
     * Write the data as JSON.
     *
     * @param listOfAnimals the records to write
     * @param out the output stream to write to
     */
    void writeJsonData(List<PostedAnimal> listOfAnimals, OutputStream out);


    /**
     * Write the data as CSV.
     *
     * @param listOfAnimals the records to write
     * @param out the output stream to write to
     */
    void writeCSVData(List<PostedAnimal> listOfAnimals, OutputStream out);


    /**
     * Write the data in the specified format.
     *
     * @param listOfAnimals the records to write
     * @param format the format to write the records in
     * @param out the output stream to write to
     */
    //static void write(List<PostedAnimal> listOfAnimals, Formats format, OutputStream out);



}
