package Model;

import java.io.OutputStream;
import java.util.List;

public interface IOutputGenerator {
    /**
     * Generate output in the specified format
     * @param animals List of animals to output
     * @param format Desired output format
     * @param outputStream Stream to write the output to
     */
    void generateOutput(List<PostedAnimal> animals, OutputFormat format, OutputStream outputStream);
} 