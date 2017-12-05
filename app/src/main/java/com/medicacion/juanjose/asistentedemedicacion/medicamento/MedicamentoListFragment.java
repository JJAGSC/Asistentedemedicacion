package com.medicacion.juanjose.asistentedemedicacion.medicamento;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.medicacion.juanjose.asistentedemedicacion.R;
import com.medicacion.juanjose.asistentedemedicacion.constantes.G;
import com.medicacion.juanjose.asistentedemedicacion.constantes.Utilidades;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.Contrato;
import com.medicacion.juanjose.asistentedemedicacion.proveedor.MedicamentoProveedor;

import java.io.FileNotFoundException;

public class MedicamentoListFragment extends ListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

	MedicamentoCursorAdapter mAdapter;
	LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	ActionMode mActionMode;
	View viewSeleccionado;

	public static MedicamentoListFragment newInstance() {
		MedicamentoListFragment f = new MedicamentoListFragment();

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem menuItem = menu.add(Menu.NONE, G.INSERTAR, Menu.NONE, "Insertar");
        menuItem.setIcon(R.drawable.ic_nuevo_registro);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case G.INSERTAR:
                Intent intent = new Intent(getActivity(), MedicamentoAddActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
	 * The Fragment's UI is just a simple text view showing its
	 * instance number.
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		//Log.i(LOGTAG, "onCreateView");
		View v = inflater.inflate(R.layout.fragment_medicamento_list, container, false);

		mAdapter = new MedicamentoCursorAdapter(getActivity());
		setListAdapter(mAdapter);

		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//Log.i(LOGTAG, "onActivityCreated");

		mCallbacks = this;

		getLoaderManager().initLoader(0, null, mCallbacks);

		getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

				if (mActionMode != null){
					return false;
				}

				mActionMode = getActivity().startActionMode(mActionModeCallback);

				view.setSelected(true);
				viewSeleccionado = view;

				return true;
			}
		});
	}

	ActionMode.Callback mActionModeCallback = new ActionMode.Callback(){

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.menu_contextual, menu);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
			switch (item.getItemId()){
				case R.id.menu_borrar:
					int medicamentoID = (Integer) viewSeleccionado.getTag();
					MedicamentoProveedor.deleteRecordConBitacora(getActivity().getContentResolver(), medicamentoID);
					break;
				case R.id.menu_editar:
					Intent intent = new Intent(getActivity(), MedicamentoModificarActivity.class);
					medicamentoID = (Integer) viewSeleccionado.getTag();
					intent.putExtra(Contrato.Medicamento._ID, medicamentoID);
					startActivity(intent);
					break;
			}
			mActionMode.finish();
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode actionMode) {
			mActionMode = null;
		}
	};

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// This is called when a new Loader needs to be created.  This
		// sample only has one Loader, so we don't care about the ID.
		// First, pick the base URI to use depending on whether we are
		// currently filtering.
		String columns[] = new String[] { Contrato.Medicamento._ID,
										  Contrato.Medicamento.NOMBRE,
				                          Contrato.Medicamento.FORMATO
										};

		Uri baseUri = Contrato.Medicamento.CONTENT_URI;

		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.

		String selection = null;

		return new CursorLoader(getActivity(), baseUri,
				columns, selection, null, null);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Swap the new cursor in.  (The framework will take care of closing the
		// old cursor once we return.)

		Uri laUriBase = Uri.parse("content://"+Contrato.AUTHORITY+"/Medicamento");
		data.setNotificationUri(getActivity().getContentResolver(), laUriBase);
		
		mAdapter.swapCursor(data);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed.  We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}

	public class MedicamentoCursorAdapter extends CursorAdapter {
		public MedicamentoCursorAdapter(Context context) {
			super(context, null, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			int ID = cursor.getInt(cursor.getColumnIndex(Contrato.Medicamento._ID));
			String nombre = cursor.getString(cursor.getColumnIndex(Contrato.Medicamento.NOMBRE));
			String formato = cursor.getString(cursor.getColumnIndex(Contrato.Medicamento.FORMATO));
	
			TextView textviewNombre = (TextView) view.findViewById(R.id.textview_medicamento_list_item_nombre);
			textviewNombre.setText(nombre);

			TextView textviewFormato = (TextView) view.findViewById(R.id.textview_medicamento_list_item_formato);
			textviewFormato.setText(formato);

			ImageView image = (ImageView) view.findViewById(R.id.image_view);

			try {
				Utilidades.loadImageFromStorage(getActivity(), "img_" + ID + ".jpg", image);
			} catch (FileNotFoundException e) {
				ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
				int color = generator.getColor(formato); //Genera un color según el nombre
				TextDrawable drawable = TextDrawable.builder()
						.buildRound(nombre.substring(0,1), color);
				image.setImageDrawable(drawable);
			}

			view.setTag(ID);

		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.medicamento_list_item, parent, false);
			bindView(v, context, cursor);
			return v;
		}
	}
}
