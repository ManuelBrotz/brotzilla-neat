package ch.brotzilla.neat.artspace;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.common.collect.Lists;

import ch.brotzilla.neat.math.ActivationFunction;
import ch.brotzilla.neat.math.ExtendedCosFunction;
import ch.brotzilla.neat.math.ExtendedElliottFunction;
import ch.brotzilla.neat.math.ExtendedGaussianFunction;
import ch.brotzilla.neat.math.ExtendedLinearFunction;
import ch.brotzilla.neat.math.ExtendedLogFunction;
import ch.brotzilla.neat.math.ExtendedSawtoothWaveFunction;
import ch.brotzilla.neat.math.ExtendedSignumFunction;
import ch.brotzilla.neat.math.ExtendedSqrtFunction;
import ch.brotzilla.neat.math.ExtendedSquareWaveFunction;
import ch.brotzilla.neat.math.ExtendedTanhFunction;
import ch.brotzilla.neat.math.ExtendedTriangleWaveFunction;
import ch.brotzilla.neat.neuralnet.BiasConnection;
import ch.brotzilla.neat.neuralnet.ComplexHiddenNeuron;
import ch.brotzilla.neat.neuralnet.ComplexOutputNeuron;
import ch.brotzilla.neat.neuralnet.Connection;
import ch.brotzilla.neat.neuralnet.HiddenNeuronConnection;
import ch.brotzilla.neat.neuralnet.HiddenNeuronSynapseConnection;
import ch.brotzilla.neat.neuralnet.InputNeuronConnection;
import ch.brotzilla.neat.neuralnet.InputNeuronSynapseConnection;
import ch.brotzilla.neat.neuralnet.NeuralNet;
import ch.brotzilla.neat.neuralnet.Neuron;
import ch.brotzilla.neat.neuralnet.OutputNeuronConnection;
import ch.brotzilla.neat.neuralnet.OutputNeuronSynapseConnection;
import ch.brotzilla.neat.neuralnet.SynapseConnection;
import ch.brotzilla.util.MersenneTwister;

public class TestImageGenerator {

    public static final int NumImages = 3000;
    public static final int ImageSize = 400;
    public static final double Section = 8;
    public static final boolean Color = false;
    public static final boolean FeedForward = true;
    public static final File SavePath = new File("./pics/");
    
    private TestImageGenerator() {}
    
    public static ActivationFunction[] getRandomActivationFunctions(MersenneTwister rng) {
        final List<ActivationFunction> list = Lists.newArrayList();
        list.add(new ExtendedCosFunction());
        list.add(new ExtendedElliottFunction());
        list.add(new ExtendedGaussianFunction());
        list.add(new ExtendedLinearFunction());
        list.add(new ExtendedLogFunction());
        list.add(new ExtendedSawtoothWaveFunction());
        list.add(new ExtendedSignumFunction());
        list.add(new ExtendedSqrtFunction());
        list.add(new ExtendedSquareWaveFunction());
        list.add(new ExtendedTriangleWaveFunction());
        list.add(new ExtendedTanhFunction());
        final List<ActivationFunction> result = Lists.newArrayList();
        final int num = 1 + rng.nextInt(4);
        for (int i = 0; i < num; i++) {
            result.add(list.remove(rng.nextInt(list.size())));
        }
        return result.toArray(new ActivationFunction[result.size()]);
    }
    
    public static double[] randomizeSynapses(MersenneTwister rng, double[] synapses) {
        for (int i = 0; i < synapses.length; i++) {
            if (rng.nextBoolean()) {
                if (i == 4) {
                    synapses[i] = rng.nextBoolean() ? -1 : 1;
                } else {
                    synapses[i] = rng.nextDouble() * 3.0 - 1.5;
                }
            }
        }
        return synapses;
    }
    
    public static NeuralNet createFeedForwardNeuralNet(MersenneTwister rng, int inputSize, int outputSize) {
        final ActivationFunction[] functions = getRandomActivationFunctions(rng);
        final int hiddenSize = 1 + rng.nextInt(6);
        final Neuron[] hiddenNeurons = new Neuron[hiddenSize], outputNeurons = new Neuron[outputSize];
        
        for (int i = 0; i < hiddenSize; i++) {
            final ActivationFunction f = functions[rng.nextInt(functions.length)];
            final Connection[] connections = new Connection[1 + inputSize];
            connections[0] = new BiasConnection(rng.nextDouble() * 2.0 - 1.0);
            for (int j = 1; j < connections.length; j++) {
                final double weight = rng.nextDouble() * 2.0 - 1.0;
                connections[j] = new InputNeuronConnection(j - 1, weight);
            }
            final SynapseConnection[] synapseConnections = new SynapseConnection[rng.nextInt(4)];
            for (int j = 0; j < synapseConnections.length; j++) {
                final int synapse = rng.nextInt(5);
                final double weight = rng.nextDouble() * 2.0 - 1.0;
                synapseConnections[j] = new InputNeuronSynapseConnection(rng.nextInt(inputSize), synapse, weight);
            }
            hiddenNeurons[i] = new ComplexHiddenNeuron(i, f, randomizeSynapses(rng, f.copySynapseDefaults()), connections, synapseConnections);
        }
        
        final ActivationFunction outputAF = new ExtendedTanhFunction();
        for (int i = 0; i < outputSize; i++) {
            final Connection[] connections = new Connection[1 + hiddenSize];
            connections[0] = new BiasConnection(rng.nextDouble() * 2.0 - 1.0);
            for (int j = 1; j < connections.length; j++) {
                final double weight = rng.nextDouble() * 2.0 - 1.0;
                connections[j] = new HiddenNeuronConnection(j - 1, weight);
            }
            final SynapseConnection[] synapseConnections = new SynapseConnection[rng.nextInt(4)];
            for (int j = 0; j < synapseConnections.length; j++) {
                final int synapse = rng.nextInt(5);
                final double weight = rng.nextDouble() * 2.0 - 1.0;
                final int type = rng.nextInt(2);
                if (type == 0) {
                    synapseConnections[j] = new InputNeuronSynapseConnection(rng.nextInt(inputSize), synapse, weight);
                } else {
                    synapseConnections[j] = new HiddenNeuronSynapseConnection(rng.nextInt(hiddenSize), synapse, weight);
                }
            }
            outputNeurons[i] = new ComplexOutputNeuron(i, outputAF, randomizeSynapses(rng, outputAF.copySynapseDefaults()), connections, synapseConnections);
        }
        
        return new NeuralNet(inputSize, hiddenSize, outputSize, hiddenNeurons, outputNeurons);
    }

    public static NeuralNet createRandomNeuralNet(MersenneTwister rng, int inputSize, int outputSize) {
        final ActivationFunction[] functions = getRandomActivationFunctions(rng);
        final int hiddenSize = 2 + rng.nextInt(6);
        final Neuron[] hiddenNeurons = new Neuron[hiddenSize], outputNeurons = new Neuron[outputSize];
        
        for (int i = 0; i < hiddenSize; i++) {
            final ActivationFunction f = functions[rng.nextInt(functions.length)];
            final Connection[] connections = new Connection[2 + rng.nextInt((inputSize + hiddenSize + outputSize))];
            connections[0] = new BiasConnection(rng.nextDouble() * 2.0 - 1.0);
            for (int j = 1; j < connections.length; j++) {
                final double weight = rng.nextDouble() * 2.0 - 1.0;
                final int type = rng.nextInt(3);
                if (type == 0) {
                    connections[j] = new InputNeuronConnection(rng.nextInt(inputSize), weight);
                } else if (type == 1) {
                    connections[j] = new HiddenNeuronConnection(rng.nextInt(hiddenSize), weight);
                } else {
                    connections[j] = new OutputNeuronConnection(rng.nextInt(outputSize), weight);
                }
            }
            final SynapseConnection[] synapseConnections = new SynapseConnection[rng.nextInt(4)];
            for (int j = 0; j < synapseConnections.length; j++) {
                final int synapse = rng.nextInt(5);
                final double weight = rng.nextDouble() * 2.0 - 1.0;
                final int type = rng.nextInt(3);
                if (type == 0) {
                    synapseConnections[j] = new InputNeuronSynapseConnection(rng.nextInt(inputSize), synapse, weight);
                } else if (type == 1) {
                    synapseConnections[j] = new HiddenNeuronSynapseConnection(rng.nextInt(hiddenSize), synapse, weight);
                } else {
                    synapseConnections[j] = new OutputNeuronSynapseConnection(rng.nextInt(outputSize), synapse, weight);
                }
            }
            hiddenNeurons[i] = new ComplexHiddenNeuron(i, f, randomizeSynapses(rng, f.copySynapseDefaults()), connections, synapseConnections);
        }
        
        final ActivationFunction outputAF = new ExtendedTanhFunction();
        for (int i = 0; i < outputSize; i++) {
            final Connection[] connections = new Connection[2 + rng.nextInt((inputSize + hiddenSize + outputSize))];
            connections[0] = new BiasConnection(rng.nextDouble() * 2.0 - 1.0);
            for (int j = 1; j < connections.length; j++) {
                final double weight = rng.nextDouble() * 2.0 - 1.0;
                final int type = rng.nextInt(3);
                if (type == 0) {
                    connections[j] = new InputNeuronConnection(rng.nextInt(inputSize), weight);
                } else if (type == 1) {
                    connections[j] = new HiddenNeuronConnection(rng.nextInt(hiddenSize), weight);
                } else {
                    connections[j] = new OutputNeuronConnection(rng.nextInt(outputSize), weight);
                }
            }
            final SynapseConnection[] synapseConnections = new SynapseConnection[rng.nextInt(4)];
            for (int j = 0; j < synapseConnections.length; j++) {
                final int synapse = rng.nextInt(5);
                final double weight = rng.nextDouble() * 2.0 - 1.0;
                final int type = rng.nextInt(3);
                if (type == 0) {
                    synapseConnections[j] = new InputNeuronSynapseConnection(rng.nextInt(inputSize), synapse, weight);
                } else if (type == 1) {
                    synapseConnections[j] = new HiddenNeuronSynapseConnection(rng.nextInt(hiddenSize), synapse, weight);
                } else {
                    synapseConnections[j] = new OutputNeuronSynapseConnection(rng.nextInt(outputSize), synapse, weight);
                }
            }
            outputNeurons[i] = new ComplexOutputNeuron(i, outputAF, randomizeSynapses(rng, outputAF.copySynapseDefaults()), connections, synapseConnections);
        }
        
        return new NeuralNet(inputSize, hiddenSize, outputSize, hiddenNeurons, outputNeurons);
    }

    public static int encodeColor(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static BufferedImage generateColorImage(MersenneTwister rng) {
        final NeuralNet nn = FeedForward ? createFeedForwardNeuralNet(rng, 3, 3) : createRandomNeuralNet(rng, 3, 3);
        final double[] inputBuffer = new double[3], hiddenBuffer = new double[nn.getNumberOfHiddenNeurons()], outputBuffer = new double[nn.getNumberOfOutputNeurons()];
        final BufferedImage image = new BufferedImage(ImageSize, ImageSize, BufferedImage.TYPE_INT_ARGB);
        final int[] buffer = new int[ImageSize * ImageSize];
        final WritableRaster raster = image.getRaster();
        final double halfSection = Section / 2.0;
        for (int y = 0; y < ImageSize; y++) {
            inputBuffer[1] = (double) y / (ImageSize - 1) * Section - halfSection;
            for (int x = 0; x < ImageSize; x++) {
                inputBuffer[0] = (double) x / (ImageSize - 1) * Section - halfSection;
                inputBuffer[2] = Math.sqrt(inputBuffer[1] * inputBuffer[1] + inputBuffer[0] * inputBuffer[0]);
                nn.compute(inputBuffer, hiddenBuffer, outputBuffer);
                final int r = Math.min(255, (int) Math.round(Math.abs(outputBuffer[0]) * 255));
                final int g = Math.min(255, (int) Math.round(Math.abs(outputBuffer[1]) * 255));
                final int b = Math.min(255, (int) Math.round(Math.abs(outputBuffer[2]) * 255));
                buffer[y * ImageSize + x] = encodeColor(255, r, g, b);
            }
        }
        raster.setDataElements(0, 0, ImageSize, ImageSize, buffer);
        return image;
    }
    
    public static BufferedImage generateImage(MersenneTwister rng) {
        final NeuralNet nn = FeedForward ? createFeedForwardNeuralNet(rng, 3, 1) : createRandomNeuralNet(rng, 3, 1);
        final double[] inputBuffer = new double[3], hiddenBuffer = new double[nn.getNumberOfHiddenNeurons()], outputBuffer = new double[nn.getNumberOfOutputNeurons()];
        final BufferedImage image = new BufferedImage(ImageSize, ImageSize, BufferedImage.TYPE_INT_ARGB);
        final int[] buffer = new int[ImageSize * ImageSize];
        final WritableRaster raster = image.getRaster();
        final double halfSection = Section / 2.0;
        for (int y = 0; y < ImageSize; y++) {
            inputBuffer[1] = (double) y / (ImageSize - 1) * Section - halfSection;
            for (int x = 0; x < ImageSize; x++) {
                inputBuffer[0] = (double) x / (ImageSize - 1) * Section - halfSection;
                inputBuffer[2] = Math.sqrt(inputBuffer[1] * inputBuffer[1] + inputBuffer[0] * inputBuffer[0]);
                nn.compute(inputBuffer, hiddenBuffer, outputBuffer);
                final int v = Math.min(255, (int) Math.round(Math.abs(outputBuffer[0]) * 255));
                buffer[y * ImageSize + x] = encodeColor(255, v, v, v);
            }
        }
        raster.setDataElements(0, 0, ImageSize, ImageSize, buffer);
        return image;
    }
    
    public static File chooseSavePath() {
        final File tmp = new File(SavePath, (FeedForward ? "feedforward" : "recurrent") + "-" + (Color ? "color" : "gray") + "-" + ImageSize + "x" + ImageSize + "-" + Section);
        return new File(tmp, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
    }
    
    public static void generateImages() throws IOException {
        final File savePath = chooseSavePath();
        if (!savePath.exists()) {
            if (!savePath.mkdirs()) {
                throw new RuntimeException("Unable to create folder: " + savePath);
            }
        }
        for (int i = 0; i < NumImages; i++) {
            System.out.println("Generating image " + (i + 1) + " of " + NumImages + "...");
            long seed = new Random().nextLong();
            File file = new File(savePath, seed + ".png");
            while (file.exists()) {
                seed = new Random().nextLong();
                file = new File(savePath, seed + ".png");
            }
            final MersenneTwister rng = new MersenneTwister(seed);
            final BufferedImage image = Color ? generateColorImage(rng) : generateImage(rng);
            ImageIO.write(image, "PNG", file);
        }
        System.out.println("Done!");
    } 
    
    public static void generateImage(long seed, File file) throws IOException {
        System.out.println("Generating image with seed " + seed + "...");
        final MersenneTwister rng = new MersenneTwister(seed);
        final BufferedImage image = generateImage(rng);
        ImageIO.write(image, "PNG", file);
        System.out.println("Done!");
    }
    
    public static void main(String[] args) throws IOException {
        generateImages();
    }
 
}
