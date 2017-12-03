package com.mojka.poisk.ui.presenter;

import com.mojka.poisk.data.model.Car;
import com.mojka.poisk.data.model.Image;
import com.mojka.poisk.ui.adapter.CarListAdapter;
import com.mojka.poisk.ui.contract.CarListContract;

import java.util.LinkedList;
import java.util.List;

public class CarListPresenterImpl implements CarListContract.Presenter {
    private CarListContract.View view;
    private CarListAdapter adapter;

    @Override
    public void start() {
        fetchCarList();
    }

    @Override
    public void setView(CarListContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchCarList() {
        List<Car> cars = new LinkedList<>();
        List<Image> images = new LinkedList<>();

        images.add(new Image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8Vj29k4hGvJu1Hx-RoY4RdKywFlSri1ftxprjOgout_LiAh0E"));

        cars.add(new Car(0, "Car #1", "0000001", images));
        cars.add(new Car(1, "Car #2", "0000002", images));
        cars.add(new Car(2, "Car #3", "0000003", images));
        cars.add(new Car(3, "Car #4", "0000004", images));
        cars.add(new Car(4, "Car #5", "0000005", images));
        cars.add(new Car(5, "Car #6", "0000006", images));
        cars.add(new Car(6, "Car #7", "0000007", images));
        cars.add(new Car(7, "Car #8", "0000008", images));

        setupAdapter(cars);
        view.setupUi();
    }

    @Override
    public void setupAdapter(List<Car> cars) {
        adapter = new CarListAdapter(cars, view.getViewActivity());
    }

    @Override
    public CarListAdapter getAdapter() {
        return adapter;
    }
}
