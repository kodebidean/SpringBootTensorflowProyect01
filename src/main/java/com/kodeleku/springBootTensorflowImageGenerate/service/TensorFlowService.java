package com.kodeleku.springBootTensorflowImageGenerate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.proto.framework.GraphDef;
import org.tensorflow.types.TString;
import org.tensorflow.types.TUint8;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.tensorflow.ndarray.buffer.DataBuffers;

@Service
@RequiredArgsConstructor
public class TensorFlowService {
    
    public byte[] generateImage(String prompt) {
        try (Graph graph = new Graph()) {
            // Cargar el modelo pre-entrenado
            byte[] graphDef = Files.readAllBytes(Paths.get("model/tensorflow_model.pb"));
            GraphDef graphDefProto = GraphDef.parseFrom(graphDef);
            graph.importGraphDef(graphDefProto);

            try (Session session = new Session(graph);
                 Tensor input = TString.scalarOf(prompt);
                 TUint8 output = (TUint8) session.runner()
                     .feed("input_tensor", input)
                     .fetch("output_tensor")
                     .run()
                     .get(0)) {
                
                byte[] imageBytes = new byte[(int)output.shape().size()];
                output.asRawTensor().data().copyTo(DataBuffers.of(imageBytes), 0);
                return imageBytes;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generating image with TensorFlow", e);
        }
    }
} 