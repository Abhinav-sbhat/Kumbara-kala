package com.kumbarakala.app.data.repository;

import android.content.Context;
import com.kumbarakala.app.data.local.dao.ArtisanDao;
import com.kumbarakala.app.data.local.dao.ProductDao;
import com.kumbarakala.app.data.local.dao.StoryCardDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ArtisanRepository_Factory implements Factory<ArtisanRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<ArtisanDao> artisanDaoProvider;

  private final Provider<ProductDao> productDaoProvider;

  private final Provider<StoryCardDao> storyCardDaoProvider;

  public ArtisanRepository_Factory(Provider<Context> contextProvider,
      Provider<ArtisanDao> artisanDaoProvider, Provider<ProductDao> productDaoProvider,
      Provider<StoryCardDao> storyCardDaoProvider) {
    this.contextProvider = contextProvider;
    this.artisanDaoProvider = artisanDaoProvider;
    this.productDaoProvider = productDaoProvider;
    this.storyCardDaoProvider = storyCardDaoProvider;
  }

  @Override
  public ArtisanRepository get() {
    return newInstance(contextProvider.get(), artisanDaoProvider.get(), productDaoProvider.get(), storyCardDaoProvider.get());
  }

  public static ArtisanRepository_Factory create(Provider<Context> contextProvider,
      Provider<ArtisanDao> artisanDaoProvider, Provider<ProductDao> productDaoProvider,
      Provider<StoryCardDao> storyCardDaoProvider) {
    return new ArtisanRepository_Factory(contextProvider, artisanDaoProvider, productDaoProvider, storyCardDaoProvider);
  }

  public static ArtisanRepository newInstance(Context context, ArtisanDao artisanDao,
      ProductDao productDao, StoryCardDao storyCardDao) {
    return new ArtisanRepository(context, artisanDao, productDao, storyCardDao);
  }
}
