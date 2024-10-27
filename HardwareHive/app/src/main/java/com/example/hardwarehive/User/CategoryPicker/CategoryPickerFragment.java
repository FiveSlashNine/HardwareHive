package com.example.hardwarehive.User.CategoryPicker;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hardwarehive.Admin.CategoryPicker.CategoryPickerInterface;
import com.example.hardwarehive.LoginOrRegister.LoginFragment;
import com.example.hardwarehive.MainActivity;
import com.example.hardwarehive.R;
import com.example.hardwarehive.User.HardwareList.HardwareListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CategoryPickerFragment extends Fragment implements CategoryPickerInterface {
    private HashMap<String, String> imageList;
    private View root;
    private FirebaseAuth mAuth;

    public CategoryPickerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.fragment_category_picker, container, false);

        RecyclerView categoryGrid = root.findViewById(R.id.pickCategoryGrid);

        initializeImageList();

        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(getContext(), new ArrayList<>(imageList.keySet()), this);
        categoryGrid.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        categoryGrid.setLayoutManager(gridLayoutManager);

        searchBarInitializer();

        logoutButtonInitializer();

        return root;
    }

    // Logs out the user
    public void logoutButtonInitializer() {
        mAuth = FirebaseAuth.getInstance();
        root.findViewById(R.id.logoutButton).setOnClickListener(v -> {
            FirebaseUser user = mAuth.getCurrentUser();
            if(user!=null) {
                mAuth.signOut();
            }
            LoginFragment loginFragment = new LoginFragment();
            ((MainActivity) requireActivity()).switchFragment(loginFragment);
        });
    }

    // Method that parses the categories.xml file to fill a hashmap with image urls for every category
    public void initializeImageList() {
        imageList = new HashMap<>();
        try {
            InputStream is = requireActivity().getAssets().open("categories.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("category");

            for (int i=0; i<nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementNode = (Element) node;
                    String name = elementNode.getElementsByTagName("name").item(0).getTextContent();
                    String link = elementNode.getElementsByTagName("link").item(0).getTextContent();

                    imageList.put(link, name);
                }
            }

        } catch (Exception ignored) {}
    }

    // Method used to switch to a different fragment
    // Passes to the fragment a title corresponding to the chosen category
    @Override
    public void moveToCategory(String url) {
        Bundle savedInstanceState = new Bundle();
        savedInstanceState.putString("title", imageList.get(url));

        HardwareListFragment hardwareListFragment = new HardwareListFragment();
        hardwareListFragment.setArguments(savedInstanceState);

       ((MainActivity) requireActivity()).switchFragment(hardwareListFragment);
    }

    private void searchBarInitializer() {
        EditText editText = root.findViewById(R.id.searchBarEditText);
        editText.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                Bundle bundle = new Bundle();
                bundle.putString("itemName", String.valueOf(editText.getText()));

                HardwareListFragment HardwareListFragment = new HardwareListFragment();
                HardwareListFragment.setArguments(bundle);

                ((MainActivity) requireActivity()).switchFragment(HardwareListFragment);
                return true;
            }

            return false;
        });
    }
}
