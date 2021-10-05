package com.narciso.cadastropessoa.utils;

public class Validacoes {
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    private static String padLeft(String text, char character) {
        return String.format("%11s", text).replace(' ', character);
    }

    public static void validaCPF(String nrCpf) {
        nrCpf = nrCpf.trim().replace(".", "").replace("-", "");
        if (nrCpf.length()!=11) {
            throw new RuntimeException(StringUtils.getMensagem("Tamanho de CPF Incorreto"));
        }

        for (int j = 0; j < 10; j++)
            if (padLeft(Integer.toString(j), Character.forDigit(j, 10)).equals(nrCpf))
                throw new RuntimeException(StringUtils.getMensagem("CPF Invállido"));

        Integer digito1 = calcularDigito(nrCpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(nrCpf.substring(0,9) + digito1, pesoCPF);
        if(!nrCpf.equals(nrCpf.substring(0,9) + digito1 + digito2))
            throw new RuntimeException(StringUtils.getMensagem("CPF Invállido"));
    }
}
