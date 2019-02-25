// AddEditFragment.java
// Fragment for adding a new contact or editing an existing one
package com.shamsul.shamsuladdressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.shamsul.shamsuladdressbook.data.DatabaseDescription.Contact;

public class AddEditFragment extends Fragment
   implements LoaderManager.LoaderCallbacks<Cursor> {

   // defines callback method implemented by MainActivity
   public interface AddEditFragmentListener {
      // called when contact is saved
      void onAddEditCompleted(Uri contactUri);
   }

   // constant used to identify the Loader
   private static final int CONTACT_LOADER = 0;

   private AddEditFragmentListener listener; // MainActivity
   private Uri contactUri; // Uri of selected contact
   private boolean addingNewContact = true; // adding (true) or editing

   // EditTexts for contact information
   private TextInputLayout nameTextInputLayout;
   private TextInputLayout phoneTextInputLayout;
   private TextInputLayout phoneTextInputLayout1;
   private TextInputLayout phoneTextInputLayout2;
   private TextInputLayout emailTextInputLayout;
   private TextInputLayout streetTextInputLayout;
   private TextInputLayout cityTextInputLayout;
   private TextInputLayout provinceTextInputLayout;
   private TextInputLayout postalCodeTextInputLayout;
   private FloatingActionButton saveContactFAB;

   private CoordinatorLayout coordinatorLayout; // used with SnackBars

   // set AddEditFragmentListener when Fragment attached
   @Override
   public void onAttach(Context context) {
      super.onAttach(context);
      listener = (AddEditFragmentListener) context;
   }

   // remove AddEditFragmentListener when Fragment detached
   @Override
   public void onDetach() {
      super.onDetach();
      listener = null;
   }

   // called when Fragment's view needs to be created
   @Override
   public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);
      setHasOptionsMenu(true); // fragment has menu items to display

      // inflate GUI and get references to EditTexts
      View view =
         inflater.inflate(R.layout.fragment_add_edit, container, false);
      nameTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.nameTextInputLayout);
      nameTextInputLayout.getEditText().addTextChangedListener(
         nameChangedListener);
      phoneTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.homephoneTextInputLayout);
      phoneTextInputLayout1 =
              (TextInputLayout) view.findViewById(R.id.workphoneTextInputLayout);
      phoneTextInputLayout2 =
              (TextInputLayout) view.findViewById(R.id.cellphoneTextInputLayout);
      emailTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.emailTextInputLayout);
      streetTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.streetTextInputLayout);
      cityTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.cityTextInputLayout);
      provinceTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.provinceTextInputLayout);
      postalCodeTextInputLayout =
         (TextInputLayout) view.findViewById(R.id.postalCodeTextInputLayout);

      // set FloatingActionButton's event listener
      saveContactFAB = (FloatingActionButton) view.findViewById(
         R.id.saveFloatingActionButton);
      saveContactFAB.setOnClickListener(saveContactButtonClicked);
      updateSaveButtonFAB();

      // used to display SnackBars with brief messages
      coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(
         R.id.coordinatorLayout);

      Bundle arguments = getArguments(); // null if creating new contact

      if (arguments != null) {
         addingNewContact = false;
         contactUri = arguments.getParcelable(MainActivity.CONTACT_URI);
      }

      // if editing an existing contact, create Loader to get the contact
      if (contactUri != null)
         getLoaderManager().initLoader(CONTACT_LOADER, null, this);

      return view;
   }

   // detects when the text in the nameTextInputLayout's EditText changes
   // to hide or show saveButtonFAB
   private final TextWatcher nameChangedListener = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count,
         int after) {}

      // called when the text in nameTextInputLayout changes
      @Override
      public void onTextChanged(CharSequence s, int start, int before,
         int count) {
         updateSaveButtonFAB();
      }

      @Override
      public void afterTextChanged(Editable s) { }
   };

   // shows saveButtonFAB only if the name is not empty
   private void updateSaveButtonFAB() {
      String input =
         nameTextInputLayout.getEditText().getText().toString();

      // if there is a name for the contact, show the FloatingActionButton
      if (input.trim().length() != 0)
         saveContactFAB.show();
      else
         saveContactFAB.hide();
   }

   // responds to event generated when user saves a contact
   private final View.OnClickListener saveContactButtonClicked =
      new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            // hide the virtual keyboard
            ((InputMethodManager) getActivity().getSystemService(
               Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
               getView().getWindowToken(), 0);

           // if(nameTextInputLayout.getEditText().getText().toString()!=null) {
               saveContact(); // save contact to the database
           // }
           // else{
            //   Toast.makeText(getActivity(),"Name Cannot be Null",Toast.LENGTH_SHORT).show();
           // }
         }
      };

   // saves contact information to the database
   private void saveContact() {
      // create ContentValues object containing contact's key-value pairs
      ContentValues contentValues = new ContentValues();
      contentValues.put(Contact.COLUMN_NAME,
         nameTextInputLayout.getEditText().getText().toString());
      contentValues.put(Contact.COLUMN_PHONE,
         phoneTextInputLayout.getEditText().getText().toString());
      contentValues.put(Contact.COLUMN_PHONE1,
              phoneTextInputLayout1.getEditText().getText().toString());
      contentValues.put(Contact.COLUMN_PHONE2,
              phoneTextInputLayout2.getEditText().getText().toString());
      contentValues.put(Contact.COLUMN_EMAIL,
         emailTextInputLayout.getEditText().getText().toString());
      contentValues.put(Contact.COLUMN_STREET,
         streetTextInputLayout.getEditText().getText().toString());
      contentValues.put(Contact.COLUMN_CITY,
         cityTextInputLayout.getEditText().getText().toString());
      contentValues.put(Contact.COLUMN_PROVINCE,
         provinceTextInputLayout.getEditText().getText().toString());
      contentValues.put(Contact.COLUMN_POSTALCODE,
         postalCodeTextInputLayout.getEditText().getText().toString());
      contentValues.put(Contact.COLUMN_USERID,((MainActivity)getActivity()).getUid());

      if (addingNewContact) {
         // use Activity's ContentResolver to invoke
         // insert on the AddressBookContentProvider
         Uri newContactUri = getActivity().getContentResolver().insert(
            Contact.CONTENT_URI, contentValues);

         if (newContactUri != null) {
            Snackbar.make(coordinatorLayout,
               R.string.contact_added, Snackbar.LENGTH_LONG).show();
            listener.onAddEditCompleted(newContactUri);
         }
         else {
            Snackbar.make(coordinatorLayout,
               R.string.contact_not_added, Snackbar.LENGTH_LONG).show();
         }
      }
      else {
         // use Activity's ContentResolver to invoke
         // insert on the AddressBookContentProvider
         int updatedRows = getActivity().getContentResolver().update(
            contactUri, contentValues, null, null);

         if (updatedRows > 0) {
            listener.onAddEditCompleted(contactUri);
            Snackbar.make(coordinatorLayout,
               R.string.contact_updated, Snackbar.LENGTH_LONG).show();
         }
         else {
            Snackbar.make(coordinatorLayout,
               R.string.contact_not_updated, Snackbar.LENGTH_LONG).show();
         }
      }
   }

   // called by LoaderManager to create a Loader
   @Override
   public Loader<Cursor> onCreateLoader(int id, Bundle args) {
      // create an appropriate CursorLoader based on the id argument;
      // only one Loader in this fragment, so the switch is unnecessary
      int res = ((MainActivity)getActivity()).getUid();
      switch (id) {

         case CONTACT_LOADER:
            return new CursorLoader(getActivity(),
               contactUri, // Uri of contact to display
               null, // null projection returns all columns
                    "user_id = '" + res +"'", // null selection returns all rows
               null, // no selection arguments
               null); // sort order
         default:
            return null;
      }
   }

   // called by LoaderManager when loading completes
   @Override
   public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      // if the contact exists in the database, display its data
      if (data != null && data.moveToFirst()) {
         // get the column index for each data item
         int nameIndex = data.getColumnIndex(Contact.COLUMN_NAME);
         int phoneIndex = data.getColumnIndex(Contact.COLUMN_PHONE);
         int phoneIndex1 = data.getColumnIndex(Contact.COLUMN_PHONE1);
         int phoneIndex2 = data.getColumnIndex(Contact.COLUMN_PHONE2);
         int emailIndex = data.getColumnIndex(Contact.COLUMN_EMAIL);
         int streetIndex = data.getColumnIndex(Contact.COLUMN_STREET);
         int cityIndex = data.getColumnIndex(Contact.COLUMN_CITY);
         int stateIndex = data.getColumnIndex(Contact.COLUMN_PROVINCE);
         int zipIndex = data.getColumnIndex(Contact.COLUMN_POSTALCODE);

         // fill EditTexts with the retrieved data
         nameTextInputLayout.getEditText().setText(
            data.getString(nameIndex));
         phoneTextInputLayout.getEditText().setText(
            data.getString(phoneIndex));
         phoneTextInputLayout1.getEditText().setText(
                 data.getString(phoneIndex1));
         phoneTextInputLayout2.getEditText().setText(
                 data.getString(phoneIndex2));
         emailTextInputLayout.getEditText().setText(
            data.getString(emailIndex));
         streetTextInputLayout.getEditText().setText(
            data.getString(streetIndex));
         cityTextInputLayout.getEditText().setText(
            data.getString(cityIndex));
         provinceTextInputLayout.getEditText().setText(
            data.getString(stateIndex));
         postalCodeTextInputLayout.getEditText().setText(
            data.getString(zipIndex));

         updateSaveButtonFAB();
      }
   }

   // called by LoaderManager when the Loader is being reset
   @Override
   public void onLoaderReset(Loader<Cursor> loader) { }
}


/**************************************************************************
 * (C) Copyright 1992-2016 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/
