package zkp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zkp.utilities.Prover;
import zkp.utilities.Util;
import zkp.utilities.Verifier;

@RestController
public class ZKPController {

    private Prover prover = new Prover();
    private Verifier verifier = new Verifier();

    @RequestMapping("/prove-transfer")
    String proveTransfer(@RequestParam("CL") String CL, @RequestParam("CR") String CR, @RequestParam("y") String y, @RequestParam("yBar") String yBar, @RequestParam("x") String x, @RequestParam("r") String r, @RequestParam("bTransfer") String bTransfer, @RequestParam("bDiff") String bDiff){
        System.out.println("prove transfer");
        System.out.println("CL: " + CL);
        System.out.println("CR: " + CR);
        System.out.println("y: " + y);
        System.out.println("yBar: " + yBar);
        System.out.println("x: " + x);
        System.out.println("r: " + r);
        System.out.println("bTransfer: " + bTransfer);
        System.out.println("bDiff: " + bDiff);
        String proof = Util.bytesToHex(prover.proveTransfer(
                Util.hexStringToByteArray(CL), Util.hexStringToByteArray(CR),
                Util.hexStringToByteArray(y), Util.hexStringToByteArray(yBar),
                Util.hexStringToByteArray(x), Util.hexStringToByteArray(r),
                Util.hexStringToByteArray(bTransfer), Util.hexStringToByteArray(bDiff)
        ));
        System.out.println("proof: " + proof);
        return proof;
    }

    @RequestMapping("/prove-burn")
    String proveBurn(@RequestParam("CL") String CL, @RequestParam("CR") String CR, @RequestParam("y") String y, @RequestParam("bTransfer") String bTransfer, @RequestParam("x") String x, @RequestParam("bDiff") String bDiff){
        System.out.println("prove burn");
        System.out.println("CL: " + CL);
        System.out.println("CR: " + CR);
        System.out.println("y: " + y);
        System.out.println("x: " + x);
        System.out.println("bTransfer: " + bTransfer);
        System.out.println("bDiff: " + bDiff);
        String proof = Util.bytesToHex(prover.proveBurn(
                Util.hexStringToByteArray(CL), Util.hexStringToByteArray(CR),
                Util.hexStringToByteArray(y), Util.hexStringToByteArray(bTransfer),
                Util.hexStringToByteArray(x), Util.hexStringToByteArray(bDiff)
        ));
        System.out.println("proof: " + proof);
        return proof;
    }

    @RequestMapping("/verify-transfer")
    boolean verifyProof(@RequestParam("input") String input){
        System.out.println("verify transfer");
        String CLn = "0x" + input.substring(2, 130);
        String CRn = "0x" + input.substring(130, 258);
        String inL = "0x" + input.substring(258, 386);
        String outL = "0x" + input.substring(386, 514);
        String inOutR = "0x" + input.substring(514, 642);
        String y = "0x" + input.substring(642, 770);
        String yBar = "0x" + input.substring(770,898);
        String proof = "0x" + input.substring(898);
        System.out.println("CLn: " + CLn);
        System.out.println("CRn: " + CRn);
        System.out.println("inL: " + inL);
        System.out.println("outL: " + outL);
        System.out.println("inOutR: " + inOutR);
        System.out.println("y: " + y);
        System.out.println("yBar: " + yBar);
        System.out.println("proof: " + proof);
        boolean isValid = verifier.verifyTransfer(
                Util.hexStringToByteArray(CLn), Util.hexStringToByteArray(CRn),
                Util.hexStringToByteArray(inL), Util.hexStringToByteArray(outL),
                Util.hexStringToByteArray(inOutR), Util.hexStringToByteArray(y),
                Util.hexStringToByteArray(yBar), Util.hexStringToByteArray(proof)
        );
        System.out.println(" >>>>> " + isValid);
        return isValid;
    }

    @RequestMapping("/verify-burn")
    boolean verifyBurn(@RequestParam("input") String input){
        System.out.println("verify burn");
        String CLn = "0x" + input.substring(2, 130);
        String CRn = "0x" + input.substring(130, 258);
        String y = "0x" + input.substring(258, 386);
        String bTransfer = "0x" + Integer.valueOf(input.substring(386, 450),16).toString();
        String proof = "0x" + input.substring(450);
        System.out.println("CLn: " + CLn);
        System.out.println("CRn: " + CRn);
        System.out.println("y: " + y);
        System.out.println("bTransfer: " + bTransfer);
        System.out.println("proof: " + proof);
        boolean isValid = verifier.verifyBurn(
                Util.hexStringToByteArray(CLn), Util.hexStringToByteArray(CRn),
                Util.hexStringToByteArray(y), Util.hexStringToByteArray(bTransfer),
                Util.hexStringToByteArray(proof)
        );
        System.out.println(" >>>>> " + isValid);
        return isValid;
    }

}