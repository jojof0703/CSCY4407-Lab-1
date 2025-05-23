
public class AESRound {
    // Complete the S-Box array with all 256 values for AES.
    private static final int[] sBox = new int[]{
        // Row 0
        0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5,
        0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
        // Row 1
        0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0,
        0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
        // Row 2
        0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc,
        0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
        // Row 3
        0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a,
        0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
        // Row 4
        0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0,
        0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
        // Row 5
        0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b,
        0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
        // Row 6
        0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85,
        0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
        // Row 7
        0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5,
        0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
        // Row 8
        0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17,
        0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
        // Row 9
        0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88,
        0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
        // Row A
        0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c,
        0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
        // Row B
        0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9,
        0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
        // Row C
        0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6,
        0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
        // Row D
        0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e,
        0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
        // Row E
        0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94,
        0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
        // Row F
        0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68,
        0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
    };

    // Substitute each byte in the state matrix using the S-Box.
    public static void subBytes(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] = sBox[state[i][j]];
            }
        }
    }

    // Shift rows of the state matrix to the left by the row index.
    public static void shiftRows(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            state[i] = leftRotate(state[i], i);
        }
    }

    // Helper method to rotate an array to the left.
    private static int[] leftRotate(int[] row, int shift) {
        int[] result = new int[4];
        for (int i = 0; i < 4; i++) {
            result[i] = row[(i + shift) % 4];
        }
        return result;
    }

    // Mix columns of the state matrix using a fixed polynomial.
    public static void mixColumns(int[][] state) {
        for (int j = 0; j < 4; j++) {
            int s0 = state[0][j];
            int s1 = state[1][j];
            int s2 = state[2][j];
            int s3 = state[3][j];

            int r0 = mul(0x02, s0) ^ mul(0x03, s1) ^ s2 ^ s3;
            int r1 = s0 ^ mul(0x02, s1) ^ mul(0x03, s2) ^ s3;
            int r2 = s0 ^ s1 ^ mul(0x02, s2) ^ mul(0x03, s3);
            int r3 = mul(0x03, s0) ^ s1 ^ s2 ^ mul(0x02, s3);

            state[0][j] = r0;
            state[1][j] = r1;
            state[2][j] = r2;
            state[3][j] = r3;
        }
    }

    // Multiply two numbers in GF(2^8).
    private static int mul(int a, int b) {
        int p = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0)
                p ^= a;
            boolean hiBitSet = (a & 0x80) != 0;
            a <<= 1;
            if (hiBitSet)
                a ^= 0x1b;
            b >>= 1;
        }
        return p & 0xFF;
    }

    // Add (XOR) the round key to the state matrix.
    public static void addRoundKey(int[][] state, int[][] roundKey) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                state[i][j] ^= roundKey[i][j];
            }
        }
    }

    // Perform one complete AES round, printing the state after each step.
    public static void aesRound(int[][] state, int[][] roundKey) {
        System.out.println("Initial State:");
        printMatrix(state);

        subBytes(state);
        System.out.println("After SubBytes:");
        printMatrix(state);

        shiftRows(state);
        System.out.println("After ShiftRows:");
        printMatrix(state);

        mixColumns(state);
        System.out.println("After MixColumns:");
        printMatrix(state);

        addRoundKey(state, roundKey);
        System.out.println("After AddRoundKey:");
        printMatrix(state);
    }

    public static void main(String[] args) {
        // Define the initial 4x4 state matrix.
        int[][] state = {
            {0x32, 0x88, 0x31, 0xe0},
            {0x43, 0x5a, 0x31, 0x37},
            {0xf6, 0x30, 0x98, 0x07},
            {0xa8, 0x8d, 0xa2, 0x34}
        };

        // Define the round key matrix.
        int[][] roundKey = {
            {0x2b, 0x28, 0xab, 0x09},
            {0x7e, 0xae, 0xf7, 0xcf},
            {0x15, 0xd2, 0x15, 0x4f},
            {0x16, 0xa6, 0x88, 0x3c}
        };

        aesRound(state, roundKey);
    }

    // Print the 4x4 state matrix in hexadecimal format.
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%02X ", val);
            }
            System.out.println();
        }
        System.out.println();
    }
}
