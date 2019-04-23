import BLAST.BlastOutput;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


import org.biojava.nbio.ws.alignment.qblast.*;




import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.ENTREZ_QUERY;
import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.MAX_NUM_SEQ;
import static org.biojava.nbio.ws.alignment.qblast.BlastAlignmentParameterEnum.ALIGNMENTS;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

public class BlastRequest {
    private BlastQuery currentQuery;

    public BlastRequest(BlastQuery currentQuery) {
        this.currentQuery = currentQuery;
    }


    public BlastOutput toBlast() {
        ArrayList<SequenceHit> toGraph = new ArrayList<SequenceHit>();
        BlastOutput BO = null;
        int numberOfResults = currentQuery.getResultCount();

        //NCBIQ query call
        NCBIQBlastService service = new NCBIQBlastService();

        // set alignment options
        NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
        props.setBlastProgram(BlastProgramEnum.blastp);
        props.setBlastDatabase("nr");
        //get blast DB from http://blast.ncbi.nlm.nih.gov/Blast.cgi?CMD=Web&PAGE_TYPE=BlastDocs&DOC_TYPE=ProgSelectionGuide

        //only set entrezQuery if it is there!
        if (currentQuery.getEntrez().length() > 0) {
//            props.setAlignmentOption(ENTREZ_QUERY, currentQuery.getEntrez() + "[Organism]");
        }


        props.setAlignmentOption(ALIGNMENTS, String.valueOf(numberOfResults));
        props.setAlignmentOption(MAX_NUM_SEQ, String.valueOf(numberOfResults));
        //props.setBlastWordSize(3);

        // set output options
        NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();

        outputProps.setOutputOption(BlastOutputParameterEnum.ALIGNMENTS, String.valueOf(numberOfResults));
        outputProps.setOutputOption(BlastOutputParameterEnum.DESCRIPTIONS, String.valueOf(numberOfResults));

        String rid = null;          // blast request ID
        FileWriter writer = null;
        BufferedReader reader = null;

        try {
            // send blast request and save request id
            rid = service.sendAlignmentRequest(currentQuery.getFastaSequence(), props);

            //This is just to display where goes the RID after you have submitted a new BLAST
            System.out.println("The newly submitted BLAST had a RID: " + rid);
            System.out.println(service.getRemoteBlastInfo());

            // wait until results become available. Alternatively, one can do other computations/send other alignment requests
            while (!service.isReady(rid)) {
                System.out.println( "Waiting for results. Sleeping for 5 seconds");
                Thread.sleep(5000);
                //reset status

            }

            //read results when they are ready
            InputStream in = service.getAlignmentResults(rid, outputProps);

            /*
             * The following code does the majority of the work in terms of
             * doing calulations and extracting output values.
             */
            BO = BlastRequest.catchBLASTOutput(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return BO;
    }

    private static BlastOutput catchBLASTOutput(InputStream in) throws Exception {
        //JAXBContext jc = JAXBContext.newInstance(BlastOutput.class);
        //Unmarshaller u = jc.createUnmarshaller();
        //return (BlastOutput) u.unmarshal(in);
        JAXBContext context = JAXBContext.newInstance(BlastOutput.class);
        Unmarshaller u = context.createUnmarshaller();

        SAXParserFactory spf = SAXParserFactory.newInstance();

        spf.setXIncludeAware(true);
        spf.setNamespaceAware(true);
        spf.setValidating(true); // Not required for JAXB/XInclude

        XMLReader xr = (XMLReader) spf.newSAXParser().getXMLReader();
        SAXSource source = new SAXSource(xr, new InputSource(in));

        return (BlastOutput) u.unmarshal(source);
    }




}
