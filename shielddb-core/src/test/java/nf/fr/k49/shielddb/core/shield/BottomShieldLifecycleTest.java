package nf.fr.k49.shielddb.core.shield;

import nf.fr.k49.shielddb.core.ShieldDBIOException;
import nf.fr.k49.shielddb.core.json.ShieldDBJsonMapper;
import nf.fr.k49.shielddb.core.storage.ShieldDBStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BottomShieldLifecycleTest {

    @Mock
    ShieldDBStorage storage;

    @Mock
    ShieldDBJsonMapper<String> mapper;

    @InjectMocks
    BottomShield<String> bottomShield;

    @Mock
    List<String> handlingContent;

    String jsonContent = "{this should be json content}";

    private void connectBottomShieldWithStorageAndMapper() {
        when(mapper.toList(any())).thenReturn(handlingContent);
        when(handlingContent.add(any())).thenReturn(true);
        when(mapper.toJson(handlingContent)).thenReturn(jsonContent);
    }

    @Test
    public void addContent() throws IOException {
        String anyString = "anyString";
        connectBottomShieldWithStorageAndMapper();

        bottomShield.add(anyString);

        verify(handlingContent).add(eq(anyString));
        verify(storage).write(eq(jsonContent));
    }

    @Test
    public void addWithReadFromStorageException() throws IOException {
        String anyString = "anyString";
        String errorMessage = "just for test";

        when(storage.read()).thenThrow(new IOException(errorMessage));

        ShieldDBIOException expectedException = assertThrows(ShieldDBIOException.class, () -> bottomShield.add(anyString));
        assertThat(expectedException.getMessage(), containsString(errorMessage));
        verify(storage, never()).write(any());
    }

    @Test
    public void addWithWriteToStorageException() throws IOException {
        String anyString = "anyString";
        String errorMessage = "just for test";
        connectBottomShieldWithStorageAndMapper();

        doThrow(new IOException(errorMessage)).when(storage).write(any());

        ShieldDBIOException expectedException = assertThrows(ShieldDBIOException.class, () -> bottomShield.add(anyString));
        assertThat(expectedException.getMessage(), containsString(errorMessage));
        verify(storage).read();
        verify(handlingContent).add(anyString);
    }
}
