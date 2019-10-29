package business;

public interface PasswordEncoder {
    String encode(CharSequence var1);
    // �н����带 ��ȣȭ �� �� ���

    boolean matches(CharSequence var1, String var2);
    // ����ڿ��� �Է¹��� �н����带 ���� �� ���
    // �տ��� raw password, �ڿ��� encoded password

    default boolean upgradeEncoding(String encodedPassword) {
        return false;
    } // upgradeEncoding
    // default �޼ҵ�� Ŀ�����ؼ� �� ������ ��ȣȭ�� ������ �� ���

} //PasswordEncoder 