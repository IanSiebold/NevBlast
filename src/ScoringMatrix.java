import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.alignment.matrices.SubstitutionMatrixHelper;
import org.biojava.nbio.alignment.SubstitutionMatrixScorer;




/**
 *
 * @author Matthew Zygowicz - Ziggy
 */
public class ScoringMatrix
{

// Prevent construction.
// Exported constants.

    /**
     * The BLOSUM-62 protein substitution matrix.
     */
    public SubstitutionMatrixHelper matrixHelper;
    public String matrixType;
    public static int[][] matrix;
    public static final int[][] blosum62Matrix = new int[][]
            {
      /*A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z  *  -*/
/*A*/ { 4,-2, 0,-2,-1,-2, 0,-2,-1,-4,-1,-1,-1,-2,-4,-1,-1,-1, 1, 0, 0, 0,-3, 0,-2,-1,-4,-4},
/*B*/ {-2, 4,-3, 4, 1,-3,-1, 0,-3,-4, 0,-4,-3, 3,-4,-2, 0,-1, 0,-1,-3,-3,-4,-1,-3, 1,-4,-4},
/*C*/ { 0,-3, 9,-3,-4,-2,-3,-3,-1,-4,-3,-1,-1,-3,-4,-3,-3,-3,-1,-1, 9,-1,-2,-2,-2,-3,-4,-4},
/*D*/ {-2, 4,-3, 6, 2,-3,-1,-1,-3,-4,-1,-4,-3, 1,-4,-1, 0,-2, 0,-1,-3,-3,-4,-1,-3, 1,-4,-4},
/*E*/ {-1, 1,-4, 2, 5,-3,-2, 0,-3,-4, 1,-3,-2, 0,-4,-1, 2, 0, 0,-1,-4,-2,-3,-1,-2, 4,-4,-4},
/*F*/ {-2,-3,-2,-3,-3, 6,-3,-1, 0,-4,-3, 0, 0,-3,-4,-4,-3,-3,-2,-2,-2,-1, 1,-1, 3,-3,-4,-4},
/*G*/ { 0,-1,-3,-1,-2,-3, 6,-2,-4,-4,-2,-4,-3, 0,-4,-2,-2,-2, 0,-2,-3,-3,-2,-1,-3,-2,-4,-4},
/*H*/ {-2, 0,-3,-1, 0,-1,-2, 8,-3,-4,-1,-3,-2, 1,-4,-2, 0, 0,-1,-2,-3,-3,-2,-1, 2, 0,-4,-4},
/*I*/ {-1,-3,-1,-3,-3, 0,-4,-3, 4,-4,-3, 2, 1,-3,-4,-3,-3,-3,-2,-1,-1, 3,-3,-1,-1,-3,-4,-4},
/*J*/ {-4,-4,-4,-4,-4,-4,-4,-4,-4, 1,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},
/*K*/ {-1, 0,-3,-1, 1,-3,-2,-1,-3,-4, 5,-2,-1, 0,-4,-1, 1, 2, 0,-1,-3,-2,-3,-1,-2, 1,-4,-4},
/*L*/ {-1,-4,-1,-4,-3, 0,-4,-3, 2,-4,-2, 4, 2,-3,-4,-3,-2,-2,-2,-1,-1, 1,-2,-1,-1,-3,-4,-4},
/*M*/ {-1,-3,-1,-3,-2, 0,-3,-2, 1,-4,-1, 2, 5,-2,-4,-2, 0,-1,-1,-1,-1, 1,-1,-1,-1,-1,-4,-4},
/*N*/ {-2, 3,-3, 1, 0,-3, 0, 1,-3,-4, 0,-3,-2, 6,-4,-2, 0, 0, 1, 0,-3,-3,-4,-1,-2, 0,-4,-4},
/*O*/ {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4, 1,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},
/*P*/ {-1,-2,-3,-1,-1,-4,-2,-2,-3,-4,-1,-3,-2,-2,-4, 7,-1,-2,-1,-1,-3,-2,-4,-2,-3,-1,-4,-4},
/*Q*/ {-1, 0,-3, 0, 2,-3,-2, 0,-3,-4, 1,-2, 0, 0,-4,-1, 5, 1, 0,-1,-3,-2,-2,-1,-1, 3,-4,-4},
/*R*/ {-1,-1,-3,-2, 0,-3,-2, 0,-3,-4, 2,-2,-1, 0,-4,-2, 1, 5,-1,-1,-3,-3,-3,-1,-2, 0,-4,-4},
/*S*/ { 1, 0,-1, 0, 0,-2, 0,-1,-2,-4, 0,-2,-1, 1,-4,-1, 0,-1, 4, 1,-1,-2,-3, 0,-2, 0,-4,-4},
/*T*/ { 0,-1,-1,-1,-1,-2,-2,-2,-1,-4,-1,-1,-1, 0,-4,-1,-1,-1, 1, 5,-1, 0,-2, 0,-2,-1,-4,-4},
/*U*/ { 0,-3, 9,-3,-4,-2,-3,-3,-1,-4,-3,-1,-1,-3,-4,-3,-3,-3,-1,-1, 9,-1,-2,-2,-2,-3,-4,-4},
/*V*/ { 0,-3,-1,-3,-2,-1,-3,-3, 3,-4,-2, 1, 1,-3,-4,-2,-2,-3,-2, 0,-1, 4,-3,-1,-1,-2,-4,-4},
/*W*/ {-3,-4,-2,-4,-3, 1,-2,-2,-3,-4,-3,-2,-1,-4,-4,-4,-2,-3,-3,-2,-2,-3,11,-2, 2,-3,-4,-4},
/*X*/ { 0,-1,-2,-1,-1,-1,-1,-1,-1,-4,-1,-1,-1,-1,-4,-2,-1,-1, 0, 0,-2,-1,-2,-1,-1,-1,-4,-4},
/*Y*/ {-2,-3,-2,-3,-2, 3,-3, 2,-1,-4,-2,-1,-1,-2,-4,-3,-1,-2,-2,-2,-2,-1, 2,-1, 7,-2,-4,-4},
/*Z*/ {-1, 1,-3, 1, 4,-3,-2, 0,-3,-4, 1,-3,-1, 0,-4,-1, 3, 0, 0,-1,-3,-2,-3,-1,-2, 4,-4,-4},
/***/ {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4, 1,-4},
/*-*/ {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4, 1},
            };



        /*










   A  R  N  D  C  Q  E  G  H  I  L  K  M  F  P  S  T  W  Y  V  B  Z  X  *
A  4 -1 -2 -2  0 -1 -1  0 -2 -1 -1 -1 -1 -2 -1  1  0 -3 -2  0 -2 -1  0 -4
R -1  5  0 -2 -3  1  0 -2  0 -3 -2  2 -1 -3 -2 -1 -1 -3 -2 -3 -1  0 -1 -4
N -2  0  6  1 -3  0  0  0  1 -3 -3  0 -2 -3 -2  1  0 -4 -2 -3  3  0 -1 -4
D -2 -2  1  6 -3  0  2 -1 -1 -3 -4 -1 -3 -3 -1  0 -1 -4 -3 -3  4  1 -1 -4
C  0 -3 -3 -3  9 -3 -4 -3 -3 -1 -1 -3 -1 -2 -3 -1 -1 -2 -2 -1 -3 -3 -2 -4
Q -1  1  0  0 -3  5  2 -2  0 -3 -2  1  0 -3 -1  0 -1 -2 -1 -2  0  3 -1 -4
E -1  0  0  2 -4  2  5 -2  0 -3 -3  1 -2 -3 -1  0 -1 -3 -2 -2  1  4 -1 -4
G  0 -2  0 -1 -3 -2 -2  6 -2 -4 -4 -2 -3 -3 -2  0 -2 -2 -3 -3 -1 -2 -1 -4
H -2  0  1 -1 -3  0  0 -2  8 -3 -3 -1 -2 -1 -2 -1 -2 -2  2 -3  0  0 -1 -4
I -1 -3 -3 -3 -1 -3 -3 -4 -3  4  2 -3  1  0 -3 -2 -1 -3 -1  3 -3 -3 -1 -4
L -1 -2 -3 -4 -1 -2 -3 -4 -3  2  4 -2  2  0 -3 -2 -1 -2 -1  1 -4 -3 -1 -4
K -1  2  0 -1 -3  1  1 -2 -1 -3 -2  5 -1 -3 -1  0 -1 -3 -2 -2  0  1 -1 -4
M -1 -1 -2 -3 -1  0 -2 -3 -2  1  2 -1  5  0 -2 -1 -1 -1 -1  1 -3 -1 -1 -4
F -2 -3 -3 -3 -2 -3 -3 -3 -1  0  0 -3  0  6 -4 -2 -2  1  3 -1 -3 -3 -1 -4
P -1 -2 -2 -1 -3 -1 -1 -2 -2 -3 -3 -1 -2 -4  7 -1 -1 -4 -3 -2 -2 -1 -2 -4
S  1 -1  1  0 -1  0  0  0 -1 -2 -2  0 -1 -2 -1  4  1 -3 -2 -2  0  0  0 -4
T  0 -1  0 -1 -1 -1 -1 -2 -2 -1 -1 -1 -1 -2 -1  1  5 -2 -2  0 -1 -1  0 -4
W -3 -3 -4 -4 -2 -2 -3 -2 -2 -3 -2 -3 -1  1 -4 -3 -2 11  2 -3 -4 -3 -2 -4
Y -2 -2 -2 -3 -2 -1 -2 -3  2 -1 -1 -2 -1  3 -3 -2 -2  2  7 -1 -3 -2 -1 -4
V  0 -3 -3 -3 -1 -2 -2 -3 -3  3  1 -2  1 -1 -2 -2  0 -3 -1  4 -3 -2 -1 -4
B -2 -1  3  4 -3  0  1 -1  0 -3 -4  0 -3 -3 -2  0 -1 -4 -3 -3  4  1 -1 -4
Z -1  0  0  1 -3  3  4 -2  0 -3 -3  1 -1 -3 -1  0 -1 -3 -2 -2  1  4 -1 -4
X  0 -1 -1 -1 -2 -1 -1 -1 -1 -1 -1 -1 -1 -1 -2  0  0 -2 -1 -1 -1 -1 -1 -4
* -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4 -4  1


        */


    public static final int [][] defaultMatrix = new int[][]{
      /*A  R  N  D  C  Q  E  G  H  I  L  K  M  F  P  S  T  W  Y  V  B  Z  X  * */
/*A*/ { 4,-1,-2,-2, 0,-1,-1, 0,-2,-1,-1,-1,-1,-2,-1, 1, 0,-3,-2, 0,-2,-1, 0,-4},
/*R*/ {-1, 5, 0,-2,-3, 1, 0,-2, 0,-3,-2, 2,-1,-3,-2,-1,-1,-3,-2,-3,-1, 0,-1,-4},
/*N*/ {-2, 0, 6, 1,-3, 0, 0, 0, 1,-3,-3, 0,-2,-3,-2, 1, 0,-4,-2,-3, 3, 0,-1,-4},
/*D*/ {-2,-2, 1, 6,-3, 0, 2,-1,-1,-3,-4,-1,-3,-3,-1, 0,-1,-4,-3,-3, 4, 1,-1,-4},
/*C*/ { 0,-3,-3,-3, 9,-3,-4,-3,-3,-1,-1,-3,-1,-2,-3,-1,-1,-2,-2,-1,-3,-3,-2,-4},
/*Q*/ {-1, 1, 0, 0,-3, 5, 2,-2, 0,-3,-2, 1, 0,-3,-1, 0,-1,-2,-1,-2, 0, 3,-1,-4},
/*E*/ {-1, 0, 0, 2,-4, 2, 5,-2, 0,-3,-3, 1,-2,-3,-1, 0,-1,-3,-2,-2, 1, 4,-1,-4},
/*G*/ { 0,-2, 0,-1,-3,-2,-2, 6,-2,-4,-4,-2,-3,-3,-2, 0,-2,-2,-3,-3,-1,-2,-1,-4},
/*H*/ {-2, 0, 1,-1,-3, 0, 0,-2, 8,-3,-3,-1,-2,-1,-2,-1,-2,-2, 2,-3, 0, 0,-1,-4},
/*I*/ {-1,-3,-3,-3,-1,-3,-3,-4,-3, 4, 2,-3, 1, 0,-3,-2,-1,-3,-1, 3,-3,-3,-1,-4},
/*L*/ {-1,-2,-3,-4,-1,-2,-3,-4,-3, 2, 4,-2, 2, 0,-3,-2,-1,-2,-1, 1,-4,-3,-1,-4},
/*K*/ {-1, 2, 0,-1,-3, 1, 1,-2,-1,-3,-2, 5,-1,-3,-1, 0,-1,-3,-2,-2, 0, 1,-1,-4},
/*M*/ {-1,-1,-2,-3,-1, 0,-2,-3,-2, 1, 2,-1, 5, 0,-2,-1,-1,-1,-1, 1,-3,-1,-1,-4},
/*F*/ {-2,-3,-3,-3,-2,-3,-3,-3,-1, 0, 0,-3, 0, 6,-4,-2,-2, 1, 3,-1,-3,-3,-1,-4},
/*P*/ {-1,-2,-2,-1,-3,-1,-1,-2,-2,-3,-3,-1,-2,-4, 7,-1,-1,-4,-3,-2,-2,-1,-2,-4},
/*S*/ { 1,-1, 1, 0,-1, 0, 0, 0,-1,-2,-2, 0,-1,-2,-1, 4, 1,-3,-2,-2, 0, 0, 0,-4},
/*T*/ { 0,-1, 0,-1,-1,-1,-1,-2,-2,-1,-1,-1,-1,-2,-1, 1, 5,-2,-2, 0,-1,-1, 0,-4},
/*W*/ {-3,-3,-4,-4,-2,-2,-3,-2,-2,-3,-2,-3,-1, 1,-4,-3,-2,11, 2,-3,-4,-3,-2,-4},
/*Y*/ {-2,-2,-2,-3,-2,-1,-2,-3, 2,-1,-1,-2,-1, 3,-3,-2,-2, 2, 7,-1,-3,-2,-1,-4},
/*V*/ { 0,-3,-3,-3,-1,-2,-2,-3,-3, 3, 1,-2, 1,-1,-2,-2, 0,-3,-1, 4,-3,-2,-1,-4},
/*B*/ {-2,-1, 3, 4,-3, 0, 1,-1, 0,-3,-4, 0,-3,-3,-2, 0,-1,-4,-3,-3, 4, 1,-1,-4},
/*Z*/ {-1, 0, 0, 1,-3, 3, 4,-2, 0,-3,-3, 1,-1,-3,-1, 0,-1,-3,-2,-2, 1, 4,-1,-4},
/*X*/ { 0,-1,-1,-1,-2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-2, 0, 0,-2,-1,-1,-1,-1,-1,-4},
/***/ {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4, 1},
            };


    ScoringMatrix()
    {
       /* if(matrixType.equals("blosum62")){
            matrix = blosum62Matrix;
        }
        else if(matrixType.equals("UserDefined")){
            matrix = userMatrix;
        }
        else{
            //default
            matrix = blosum62Matrix;
        }
        */
       this.matrixType = "UserDefined";
       matrix = defaultMatrix;


    }


    public int getCharScore(char x, char y){

        int newY = 0;
        int newX = 0;
        String validLetters = "";
        AminoAcidCompoundSet as1 = new AminoAcidCompoundSet();
        if(matrixType.equals("UserDefined")){
            validLetters = "ARNDCQEGHILKMFPSTWYVBZX*";
            newY = validLetters.indexOf(y);
            newX = validLetters.indexOf(x);
            if(newY == -1)
                newY = 23; //may be a bug. don't know if past dev knew arrays were zero indexed
            if(newX == -1)
                newX = 23; //may be a bug. don't know if past dev knew arrays were zero indexed

        }
        else{
            return SubstitutionMatrixHelper.getAminoAcidSubstitutionMatrix(matrixType).getValue(as1.getCompoundForString(Character.toString(x)), as1.getCompoundForString(Character.toString(y)));
        }

//        System.out.println(newX + " : " + newY + " | " + defaultMatrix[newX][newY]);
        return defaultMatrix[newY][newX];
    }



}